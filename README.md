# CN Lab — Ubuntu quick run

This repo contains three Tcl scripts used for computer networks lab exercises:

- `p1.tcl`
- `p2.tcl`
- `p3.tcl`

This README gives a minimal, Ubuntu-focused set of steps to run them.

## 1) Decide interpreter

Open a script (for example `p1.tcl`) and look for NS-2-specific lines such as:

- `set ns [new Simulator]`
- strings like `Agent/`, `Queue/`, `Simulator` or use of `tracefile`, `nam` etc.

If you see those, use the `ns` command (NS-2). Otherwise use `tclsh`.

## 2) Install prerequisites (Ubuntu)

For NS-2 (common):

```bash
sudo apt update
sudo apt install -y build-essential tcl-dev tk-dev libxmu-dev xgraph
```

If you need a full NS-2 (e.g. ns-2.35) you may download and build it; many students run NS-2 in WSL or a Linux VM.

For plain Tcl scripts only:

```bash
sudo apt update
sudo apt install ns2 nam
```

## 3) Run a script (examples)

Run with NS-2:

```bash
ns p1.tcl
```

## 4) Expected outputs

- NS-2 scripts typically write trace files (e.g. `trace`, `out.tr`, `.nam`) — check the script for filenames.
- Plain Tcl scripts usually print to stdout.

## Troubleshooting

- "ns: command not found": NS-2 is not installed or not in PATH. Install NS-2 in Ubuntu/WSL or run the script with `tclsh` if appropriate.
- Permission issues: ensure files are readable.
- If a script depends on extra data files, open the top of the script for comments.

