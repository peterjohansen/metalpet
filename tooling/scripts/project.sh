#!/usr/bin/env bash
# project.sh - metalpet-specific tooling configuration.

TOOLING_SCRIPTS_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
TOOLING_BIN_DIR="$TOOLING_SCRIPTS_DIR/bin"
PROJECT_ROOT="$(cd "$TOOLING_SCRIPTS_DIR/../.." && pwd)"

source "$TOOLING_BIN_DIR/checks.sh"

check_all() {
    _checks_failed=0
    bold "Checking development environment..."

    header "Common"
    _run_required check_just
    _run_required check_dprint

    echo "" >&2
    if [[ "$_checks_failed" -eq 0 ]]; then
        success "All required dependencies present."
        return 0
    fi
    error "One or more required dependencies are missing. See messages above."
    return 1
}

placeholder_recipe() {
    local recipe="${1:-}" detail="${2:-}"
    [[ -n "$recipe" ]] || {
        error "placeholder_recipe: recipe name is required"
        return 2
    }

    if [[ -z "$detail" ]]; then
        detail="This recipe is not implemented for metalpet yet."
    fi

    error "$recipe: $detail"
    return 1
}

run_markdown_formatter() {
    local mode="${1:-}"
    local -a targets=()

    case "$mode" in
        fmt|check) ;;
        *)
            error "run_markdown_formatter: mode must be 'fmt' or 'check'"
            return 2
            ;;
    esac

    [[ -f "$PROJECT_ROOT/README.md" ]] && targets+=("$PROJECT_ROOT/README.md")
    [[ -f "$PROJECT_ROOT/AGENTS.md" ]] && targets+=("$PROJECT_ROOT/AGENTS.md")
    [[ -d "$PROJECT_ROOT/docs" ]] && targets+=("$PROJECT_ROOT/docs")

    [[ "${#targets[@]}" -gt 0 ]] || {
        warn "No markdown files found to format."
        return 0
    }

    dprint "$mode" --config "$PROJECT_ROOT/tooling/dprint.json" "${targets[@]}"
}
