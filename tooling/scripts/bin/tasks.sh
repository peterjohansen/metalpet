#!/usr/bin/env bash
# Task runner with output suppression, live indicator, and elapsed time.
#
# Usage:
#   run_tasks "label" command [arg ...] -- [ "label" command [arg ...] -- ... ]
#
# Each task runs in a backgrounded subshell with stdout+stderr captured.
# A live indicator is shown per task in TTY mode. Output is silently
# discarded on success and dumped on failure. Fail-fast: when any task
# fails, still-running tasks are terminated.

set -uo pipefail

if ! declare -F success >/dev/null 2>&1; then
    source "$(dirname "${BASH_SOURCE[0]}")/colors.sh"
fi

_fmt_dur() {
    local s=$1
    if [ "$s" -lt 60 ]; then
        printf '%ds' "$s"
    else
        printf '%dm%ds' $((s / 60)) $((s % 60))
    fi
}

_run_tasks_exec() {
    local arg_file="${1:-}" arg
    local -a cmd=()
    [[ -n "$arg_file" && -f "$arg_file" ]] || return 2
    while IFS= read -r -d '' arg; do
        cmd+=("$arg")
    done <"$arg_file"
    [[ "${#cmd[@]}" -gt 0 ]] || return 2
    "${cmd[@]}"
}

run_tasks() {
    local -a names=() arg_files=() pids=() logs=() statuses=() durations=() cancelled=()
    local tmpdir
    tmpdir=$(mktemp -d) || {
        error "run_tasks: failed to create temporary directory"
        return 1
    }

    local label arg_file saw_cmd task_index=0
    while [ "$#" -gt 0 ]; do
        label="${1:-}"
        [ -n "$label" ] || {
            rm -rf "$tmpdir"
            error "run_tasks: missing task label"
            return 2
        }
        shift

        saw_cmd=0
        arg_file="$tmpdir/task-$task_index.argv"
        : >"$arg_file"
        while [ "$#" -gt 0 ]; do
            if [ "$1" = "--" ]; then
                shift
                break
            fi
            printf '%s\0' "$1" >>"$arg_file"
            saw_cmd=1
            shift
        done
        if [ "$saw_cmd" -ne 1 ]; then
            rm -rf "$tmpdir"
            error "run_tasks: malformed task for '$label' (expected label + command args + --)"
            return 2
        fi
        names+=("$label")
        arg_files+=("$arg_file")
        task_index=$((task_index + 1))
    done

    local n=${#names[@]}
    [ "$n" -eq 0 ] && {
        rm -rf "$tmpdir"
        return 0
    }

    local i
    for i in "${!names[@]}"; do
        logs+=("$tmpdir/$i.log")
        statuses+=("-1")
        durations+=("0")
        cancelled+=("0")
    done

    _run_tasks_kill_all() {
        local pid
        for pid in "${pids[@]:-}"; do
            if [ -n "$pid" ] && kill -0 "$pid" 2>/dev/null; then
                kill "$pid" 2>/dev/null || true
            fi
        done
    }
    _run_tasks_cleanup() {
        _run_tasks_kill_all
        rm -rf "${tmpdir:-}"
    }
    trap '_run_tasks_cleanup; trap - INT TERM RETURN; exit 130' INT TERM
    trap '_run_tasks_cleanup' RETURN

    local tty=0
    [ -t 2 ] && [ -z "${NO_COLOR:-}" ] && tty=1

    local total_start
    total_start=$(date +%s)
    local -a starts=()
    for i in "${!names[@]}"; do
        starts+=("$(date +%s)")
        if [ "$tty" -eq 1 ]; then
            (
                FORCE_COLOR=1 CLICOLOR_FORCE=1 \
                _run_tasks_exec "${arg_files[$i]}"
            ) >"${logs[$i]}" 2>&1 &
        else
            ( _run_tasks_exec "${arg_files[$i]}" ) >"${logs[$i]}" 2>&1 &
        fi
        pids+=($!)
    done

    local frames=("⠋" "⠙" "⠹" "⠸" "⠼" "⠴" "⠦" "⠧" "⠇" "⠏")
    local frame=0

    _render_line_nontty() {
        local i=$1 d
        d=$(_fmt_dur "${durations[$i]}")
        if [ "${cancelled[$i]}" = "1" ]; then
            printf '%s⊘%s %s %s(cancelled, %s)%s\n' \
                "$_YELLOW" "$_RESET" "${names[$i]}" "$_DIM" "$d" "$_RESET" >&2
        elif [ "${statuses[$i]}" -eq 0 ]; then
            printf '%s✔%s %s %s(%s)%s\n' \
                "$_GREEN" "$_RESET" "${names[$i]}" "$_DIM" "$d" "$_RESET" >&2
        else
            printf '%s✖%s %s %s(exit %s, %s)%s\n' \
                "$_RED" "$_RESET" "${names[$i]}" "$_DIM" "${statuses[$i]}" "$d" "$_RESET" >&2
        fi
    }

    if [ "$tty" -eq 1 ]; then
        for i in "${!names[@]}"; do
            printf '%s%s%s %s\n' "$_BLUE" "${frames[0]}" "$_RESET" "${names[$i]}" >&2
        done
    else
        for i in "${!names[@]}"; do
            printf '%s▶%s %s\n' "$_BLUE" "$_RESET" "${names[$i]}" >&2
        done
    fi

    local aborting=0
    while :; do
        local all_done=1
        local any_failed=0
        local now
        now=$(date +%s)
        for i in "${!names[@]}"; do
            if [ "${statuses[$i]}" = "-1" ]; then
                if ! kill -0 "${pids[$i]}" 2>/dev/null; then
                    local rc=0
                    wait "${pids[i]}" || rc=$?
                    statuses[i]=$rc
                    durations[i]=$(( now - starts[i] ))
                    if [ "${cancelled[i]}" != "1" ] && [ "${statuses[i]}" -ne 0 ]; then
                        any_failed=1
                    fi
                    [ "$tty" -ne 1 ] && _render_line_nontty "$i"
                else
                    durations[i]=$(( now - starts[i] ))
                    all_done=0
                fi
            fi
        done

        if [ "$any_failed" -eq 1 ] && [ "$aborting" -eq 0 ]; then
            aborting=1
            local j
            for j in "${!names[@]}"; do
                if [ "${statuses[j]}" = "-1" ]; then
                    cancelled[j]=1
                fi
            done
            _run_tasks_kill_all
        fi

        if [ "$tty" -eq 1 ]; then
            printf '\033[%dA' "$n" >&2
            local d
            for i in "${!names[@]}"; do
                printf '\r\033[2K' >&2
                d=$(_fmt_dur "${durations[$i]}")
                if [ "${cancelled[$i]}" = "1" ] && [ "${statuses[$i]}" = "-1" ]; then
                    printf '%s⊘%s %s %s(cancelling, %s)%s\n' \
                        "$_YELLOW" "$_RESET" "${names[$i]}" "$_DIM" "$d" "$_RESET" >&2
                elif [ "${cancelled[$i]}" = "1" ]; then
                    printf '%s⊘%s %s %s(cancelled, %s)%s\n' \
                        "$_YELLOW" "$_RESET" "${names[$i]}" "$_DIM" "$d" "$_RESET" >&2
                else
                    case "${statuses[$i]}" in
                        -1) printf '%s%s%s %s %s(%s)%s\n' \
                                "$_BLUE" "${frames[$frame]}" "$_RESET" "${names[$i]}" "$_DIM" "$d" "$_RESET" >&2 ;;
                        0)  printf '%s✔%s %s %s(%s)%s\n' \
                                "$_GREEN" "$_RESET" "${names[$i]}" "$_DIM" "$d" "$_RESET" >&2 ;;
                        *)  printf '%s✖%s %s %s(exit %s, %s)%s\n' \
                                "$_RED" "$_RESET" "${names[$i]}" "$_DIM" "${statuses[$i]}" "$d" "$_RESET" >&2 ;;
                    esac
                fi
            done
            frame=$(( (frame + 1) % ${#frames[@]} ))
        fi

        [ "$all_done" -eq 1 ] && break
        sleep 0.2
    done

    local total_dur
    total_dur=$(_fmt_dur $(( $(date +%s) - total_start )))

    local failed=0
    for i in "${!names[@]}"; do
        if [ "${cancelled[$i]}" != "1" ] && [ "${statuses[$i]}" -ne 0 ]; then
            failed=1
            echo "" >&2
            dim "── ${names[$i]} output ──"
            cat "${logs[$i]}" >&2
            dim "── end ${names[$i]} ──"
        fi
    done

    if [ "${RUN_TASKS_NO_SUMMARY:-0}" != "1" ]; then
        echo "" >&2
        if [ "$failed" -eq 0 ]; then
            success "Done in ${total_dur}."
        else
            error "Failed after ${total_dur}."
        fi
    fi

    return $failed
}
