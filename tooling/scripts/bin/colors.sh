#!/usr/bin/env bash
# colors.sh — ANSI color constants and styled output helpers.
# All output goes to stderr so recipes can pipe stdout cleanly.

if [[ -t 2 ]] && [[ -z "${NO_COLOR:-}" ]]; then
    _RED=$'\033[0;31m'
    _GREEN=$'\033[0;32m'
    _YELLOW=$'\033[0;33m'
    _BLUE=$'\033[0;34m'
    _CYAN=$'\033[0;36m'
    _DIM=$'\033[2m'
    _BOLD=$'\033[1m'
    _RESET=$'\033[0m'
else
    _RED='' _GREEN='' _YELLOW='' _BLUE='' _CYAN='' _DIM='' _BOLD='' _RESET=''
fi

info()    { printf '%sℹ%s %s\n' "$_BLUE"   "$_RESET" "$*" >&2; }
success() { printf '%s✔%s %s\n' "$_GREEN"  "$_RESET" "$*" >&2; }
warn()    { printf '%s⚠%s %s\n' "$_YELLOW" "$_RESET" "$*" >&2; }
error()   { printf '%s✖%s %s\n' "$_RED"    "$_RESET" "$*" >&2; }
dim()     { printf '%s%s%s\n'   "$_DIM"    "$*"      "$_RESET" >&2; }
bold()    { printf '%s%s%s\n'   "$_BOLD"   "$*"      "$_RESET" >&2; }
header()  { printf '\n%s%s── %s ──%s\n' "$_BOLD" "$_CYAN" "$*" "$_RESET" >&2; }
