import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static int count;

    private static void increment() {
        LOCK.lock();
        try { count++; }
        finally { LOCK.unlock(); }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(() -> { for (int i = 0; i < 10_000; i++) increment(); });
        Thread b = new Thread(() -> { for (int i = 0; i < 10_000; i++) increment(); });
        a.start(); b.start(); a.join(); b.join();
        System.out.println("Count: " + count);
    }
}
