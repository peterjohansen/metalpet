# Metalpet

Agentic instruction for metalpet.

## Overview

- **Language**: Rust (coming soon)

## Commands

```sh
just format # Format the codebase
just format-check # Check formatting
just --list # Full command reference
```

## Git workflow

- Use `main` as the default branch.
- Always branch from the latest `main` and submit changes as pull requests.
- Never commit directly to `main` unless explicitly given permission by the
  user.
- Use branch naming: `feat/<topic>`, `fix/<topic>`, `docs/<topic>`,
  `chore/<topic>`.
- Include issue tracker keys in branch names when available (e.g.
  `feat/42-add-export`, `fix/FOO-1234-null-pointer`).
- Ensure CI/CD passes before considering work done.
- Resolve pull request conversations when addressed; always reply with a concise
  summary of the conclusion.

## Commit conventions

- Use Conventional Commits format: `feat:`, `fix:`, `docs:`, `test:`, `chore:`,
  `refactor:`, `build:`, `ci:`, `perf:`, `style:`.
- Include issue tracker keys in commit messages when available.
- Prefer smaller, logically related commits — one concern, one commit.

## Before committing

- Run `just format-check`.
- Run tests before committing.
- Do not commit if any check fails locally.

## Code style

- Comment only where clarification is needed. Do not comment obvious code.
- Follow existing patterns in the codebase. When in doubt, look at how similar
  things are already done.
- No unsolicited refactoring.
- Do not modify code outside the scope of your task.
- Do not add libraries, tools, packages, or external code without explicit user
  approval.
- Do not fix pre-existing issues unrelated to your task. If a pre-existing issue
  blocks your work, note it and ask for guidance.
- Write tests for new functionality when test infrastructure exists. Match the
  existing test style.
- If a task is ambiguous or blocked, state the ambiguity clearly and ask for
  clarification rather than guessing.

## Documentation

- Inter-document references and links are encouraged to create a navigable
  knowledge network.
- When making code changes, update the relevant documentation in the same commit
  or PR.
- Keep cross-references between documents valid after file moves or renames.
- Keep documentation concise and as tight as possible while still being clear.
- Comment in code only where clarification is needed.
- Markdown: lower-kebab-case filenames (except `README.md`, `AGENTS.md`, etc.);
  sentence case headings, except title case in the main heading.

## Tooling

- Keep tooling recipes up to date when the project gains new tools,
  dependencies, or formatting steps.
- Do not introduce new tooling or recipes without explicit user approval.

## Documentation reference

Only read detailed documentation files if directly relevant to your current
task. Do not load documents speculatively.

- [`docs/template-recipe.md`](docs/philosophy.md) — Thoughts about the vision of
  Metalpet
- [`docs/template-adoption.md`](docs/template-adoption.md) — The process of
  adopting Metalpet templates as projects
- [`docs/philosophy.md`](docs/philosophy.md) — Thoughts about the vision of
  Metalpet
- [`docs/development-notes.md`](docs/development-notes.md) — Notes for the
  initial development of Metalpet
