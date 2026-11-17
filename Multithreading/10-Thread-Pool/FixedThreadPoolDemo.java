import java.util.concurrent.*;

public class FixedThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        try {
            for (int i = 1; i <= 6; i++) {
                int id = i;
                pool.submit(() -> System.out.println("Task " + id + " -> " + Thread.currentThread().getName()));
            }
        } finally {
            pool.shutdown();
        }
    }
}
