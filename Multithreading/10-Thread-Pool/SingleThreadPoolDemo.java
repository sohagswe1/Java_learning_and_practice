import java.util.concurrent.*;

public class SingleThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try {
            for (int i = 1; i <= 4; i++) {
                int id = i;
                pool.submit(() -> System.out.println("Task " + id + " on " + Thread.currentThread().getName()));
            }
        } finally {
            pool.shutdown();
        }
    }
}
