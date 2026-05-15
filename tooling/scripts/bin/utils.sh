#!/usr/bin/env bash
# utils.sh — Generic utilities for shell helpers.

detect_os() {
    case "$(uname -s)" in
        Darwin*)              echo "macos"   ;;
        Linux*)               echo "linux"   ;;
        MINGW*|MSYS*|CYGWIN*) echo "windows" ;;
        *)                    echo "unknown" ;;
    esac
}
