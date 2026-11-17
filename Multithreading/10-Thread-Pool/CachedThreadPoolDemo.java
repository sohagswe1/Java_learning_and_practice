import java.util.concurrent.*;

public class CachedThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        try {
            for (int i = 1; i <= 5; i++) {
                int id = i;
                pool.submit(() -> System.out.println("Task " + id + " on " + Thread.currentThread().getName()));
            }
        } finally {
            pool.shutdown();
        }
    }
}
