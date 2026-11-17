# 07 - Inter-Thread Communication

`wait()`, `notify()`, and `notifyAll()` coordinate threads using an object's monitor. They must be called while owning that object's monitor. Always check the condition in a `while` loop.
