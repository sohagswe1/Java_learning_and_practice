import java.util.concurrent.locks.*;

public class ReadWriteLockDemo {
    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();
    private static int value = 0;

    private static void write(int newValue) {
        LOCK.writeLock().lock();
        try { value = newValue; }
        finally { LOCK.writeLock().unlock(); }
    }

    private static int read() {
        LOCK.readLock().lock();
        try { return value; }
        finally { LOCK.readLock().unlock(); }
    }

    public static void main(String[] args) {
        write(42);
        System.out.println("Value: " + read());
    }
}
