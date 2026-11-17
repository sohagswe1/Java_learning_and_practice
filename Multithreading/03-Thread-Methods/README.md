# 03 - Thread Methods

Examples of `sleep()`, `join()`, `yield()`, `interrupt()`, thread naming, and current-thread information.

- `sleep()` pauses the current thread; it does not release monitor locks.
- `join()` waits for another thread to finish.
- `yield()` is only a scheduling hint.
- `interrupt()` requests cooperative interruption; it does not forcibly kill a thread.
