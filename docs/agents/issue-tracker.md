# Issue tracker

This repository tracks work using GitHub Issues.

## What this means

Engineering skills such as `to-issues`, `triage`, `to-prd`, and `qa` will read and write issue data through GitHub Issues.

## How skills will use it

- `to-issues` can create new GitHub issues when generating work items.
- `triage` can read issue text and apply labels to move issues through the triage state machine.
- `to-prd` and `qa` can reference issue content for planning and quality checks.

## Tooling

These skills expect the `gh` CLI to be available and authenticated for the current repository.

### Recommended setup

1. Install GitHub CLI: https://cli.github.com/
2. Authenticate: `gh auth login`
3. Confirm access from this repo: `gh repo view`

## Notes

This repo has a GitHub remote at `https://github.com/RocioBelenI/CarreraCaballos.git`, so GitHub is the natural issue tracker for this project.
