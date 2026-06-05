# Domain docs

This repository uses a single-context layout.

## Layout

- `CONTEXT.md` is expected at the repository root.
- Architectural decision records are expected under `docs/adr/`.

## Why this matters

Skills like `improve-codebase-architecture`, `diagnose`, and `tdd` read `CONTEXT.md` to learn the project’s domain language and look in `docs/adr/` for past decisions.

## Single-context rule

With the single-context layout, agent tooling assumes one global context for the whole repository.

## If the repo becomes a monorepo later

If this repository later splits into multiple context areas, add a root-level `CONTEXT-MAP.md` that points to per-context `CONTEXT.md` files and update this document accordingly.
