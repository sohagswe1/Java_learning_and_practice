import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockDemo {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        if (lock.tryLock(1, TimeUnit.SECONDS)) {
            try { System.out.println("Lock acquired"); }
            finally { lock.unlock(); }
        } else {
            System.out.println("Could not acquire lock in time");
        }
    }
}
