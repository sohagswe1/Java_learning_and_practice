public class DeadlockPreventionByLockOrdering {
    private static final Object A = new Object();
    private static final Object B = new Object();

    private static void work(String name) {
        synchronized (A) {
            synchronized (B) {
                System.out.println(name + " acquired A then B");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(() -> work("Thread-1"));
        Thread second = new Thread(() -> work("Thread-2"));
        first.start(); second.start();
        first.join(); second.join();
    }
}
