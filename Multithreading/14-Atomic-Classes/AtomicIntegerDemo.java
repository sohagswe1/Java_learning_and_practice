import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger count = new AtomicInteger();
        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) count.incrementAndGet();
        };

        Thread a = new Thread(task);
        Thread b = new Thread(task);
        a.start(); b.start(); a.join(); b.join();
        System.out.println("Count: " + count.get());
    }
}
