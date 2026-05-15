#!/usr/bin/env bash
# checks.sh — Generic dependency checks for development tooling.

TOOLING_BIN_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$TOOLING_BIN_DIR/colors.sh"

check_just() {
    if ! command -v just &>/dev/null; then
        error "just is not installed. Install: https://github.com/casey/just#installation"
        return 1
    fi
    success "just $(just --version 2>/dev/null | awk '{print $2}')"
}

check_dprint() {
    if ! command -v dprint &>/dev/null; then
        error "dprint is not installed. Install: https://dprint.dev/install/"
        return 1
    fi
    success "dprint $(dprint --version 2>/dev/null | awk '{print $2}')"
}

check_docker() {
    if ! command -v docker &>/dev/null; then
        error "Docker is not installed. Install: https://docs.docker.com/get-docker/"
        return 1
    fi
    if ! docker info &>/dev/null; then
        error "Docker is installed but the daemon is not running."
        return 1
    fi
    if ! docker compose version &>/dev/null; then
        error "Docker Compose v2 is required but 'docker compose' is unavailable."
        return 1
    fi
    success "Docker and Docker Compose are available"
}

check_java() {
    local required_major="${1:-17}"
    if ! command -v java &>/dev/null; then
        error "Java is not installed. Install JDK ${required_major}+."
        return 1
    fi
    local raw major
    raw=$(java -version 2>&1 | grep -i 'version' | head -1 | sed -E 's/.*"([0-9]+).*/\1/')
    major=${raw:-0}
    if [[ "$major" -ge "$required_major" ]] 2>/dev/null; then
        success "Java $major"
        return 0
    fi
    error "JDK ${required_major}+ is required (found: ${major:-unknown})."
    return 1
}

check_maven() {
    local required_major="${1:-3}" required_minor="${2:-9}"
    if ! command -v mvn &>/dev/null; then
        error "Maven is not installed. Install: https://maven.apache.org/install.html"
        return 1
    fi
    local version major minor
    version=$(mvn -version 2>/dev/null | head -1 | sed -E 's/.*([0-9]+\.[0-9]+\.[0-9]+).*/\1/')
    major=$(echo "$version" | cut -d. -f1)
    minor=$(echo "$version" | cut -d. -f2)
    if [[ "$major" -gt "$required_major" ]] 2>/dev/null \
       || { [[ "$major" -eq "$required_major" ]] && [[ "$minor" -ge "$required_minor" ]]; } 2>/dev/null; then
        success "Maven $version"
        return 0
    fi
    error "Maven ${required_major}.${required_minor}+ is required (found: ${version:-unknown})."
    return 1
}

check_pnpm() {
    if ! command -v pnpm &>/dev/null; then
        error "pnpm is not installed. Install: https://pnpm.io/installation"
        return 1
    fi
    success "pnpm $(pnpm --version 2>/dev/null)"
}

check_node() {
    local required_major="${1:-20}"
    if ! command -v node &>/dev/null; then
        error "Node.js is not installed. Install: https://nodejs.org/"
        return 1
    fi
    local version major
    version=$(node --version 2>/dev/null | sed -E 's/^v//')
    major=$(echo "$version" | cut -d. -f1)
    if [[ "$major" -ge "$required_major" ]] 2>/dev/null; then
        success "Node.js $version"
        return 0
    fi
    error "Node.js ${required_major}+ is required (found: ${version:-unknown})."
    return 1
}

require() {
    local fn out rc
    for fn in "$@"; do
        rc=0
        out=$("$fn" 2>&1) || rc=$?
        if [[ "$rc" -ne 0 ]]; then
            printf '%s\n' "$out" >&2
            exit "$rc"
        fi
    done
}

_run_required() {
    if ! "$@"; then
        _checks_failed=1
    fi
}
