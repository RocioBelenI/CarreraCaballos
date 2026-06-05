# Triage labels

This repository uses the standard triage label vocabulary.

## Canonical roles

- `needs-triage` — maintainer needs to evaluate the issue.
- `needs-info` — waiting on the reporter for more information.
- `ready-for-agent` — fully specified and ready for an AFK agent to pick up.
- `ready-for-human` — needs a human to implement or review.
- `wontfix` — will not be actioned.

## Usage

The `triage` skill relies on these label names when it processes issues. Because the repository currently uses the standard names, no custom mapping is required.

## If you change labels later

If your project later adopts different label names, update this document with the new mappings so agent tooling can apply the correct labels instead of creating duplicates.
