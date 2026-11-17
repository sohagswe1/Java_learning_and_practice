# 02 - Thread Lifecycle

Java thread states: `NEW`, `RUNNABLE`, `BLOCKED`, `WAITING`, `TIMED_WAITING`, and `TERMINATED`.

`RUNNABLE` includes both ready-to-run and running. `getState()` returns only a snapshot; do not use it for synchronization.
