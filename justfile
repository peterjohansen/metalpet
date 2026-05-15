set shell := ["bash", "-cu"]

default:
    @just --list --unsorted

# ─── 1. Essentials ──────────────────────────────────────────────────────

# Verify all required development dependencies are installed.
[group('1. Essentials')]
setup:
    #!/usr/bin/env bash
    set -uo pipefail
    source tooling/scripts/project.sh
    check_all

# ─── 2. Build ───────────────────────────────────────────────────────────

# Build the project with tests.
[group('2. Build')]
[no-exit-message]
build:
    #!/usr/bin/env bash
    set -euo pipefail
    source tooling/scripts/project.sh
    placeholder_recipe build \
        "Build is not implemented for metalpet yet because the project implementation has not been added."

# Build the project without running tests.
[group('2. Build')]
[no-exit-message]
build-fast:
    #!/usr/bin/env bash
    set -euo pipefail
    source tooling/scripts/project.sh
    placeholder_recipe build-fast \
        "Fast build is not implemented for metalpet yet because the project implementation has not been added."

# Remove all build artifacts.
[group('2. Build')]
[no-exit-message]
build-clean:
    #!/usr/bin/env bash
    set -euo pipefail
    source tooling/scripts/project.sh
    placeholder_recipe build-clean \
        "Build cleanup is not implemented for metalpet yet because the project implementation has not been added."

# ─── 3. Linting ─────────────────────────────────────────────────────────

# Auto-format the entire repository.
[group('3. Linting')]
[no-exit-message]
format:
    #!/usr/bin/env bash
    set -euo pipefail
    source tooling/scripts/project.sh
    source tooling/scripts/bin/tasks.sh
    require check_dprint
    run_tasks \
        "markdown format" run_markdown_formatter fmt --

# Verify formatting without modifying files.
[group('3. Linting')]
[no-exit-message]
format-check:
    #!/usr/bin/env bash
    set -euo pipefail
    source tooling/scripts/project.sh
    source tooling/scripts/bin/tasks.sh
    require check_dprint
    run_tasks \
        "markdown format-check" run_markdown_formatter check --

# ─── 4. Testing ─────────────────────────────────────────────────────────

# Run all tests, or run matching server/client tests for a pattern.
[group('4. Testing')]
[no-exit-message]
test *pattern:
    #!/usr/bin/env bash
    set -euo pipefail
    source tooling/scripts/project.sh
    pattern='{{pattern}}'
    if [[ -n "$pattern" ]]; then
        placeholder_recipe test \
            "Testing is not implemented for metalpet yet. The requested pattern '$pattern' cannot be used until the project implementation exists."
    else
        placeholder_recipe test \
            "Testing is not implemented for metalpet yet because the project implementation has not been added."
    fi
