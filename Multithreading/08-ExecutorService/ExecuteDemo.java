import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuteDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            for (int i = 1; i <= 4; i++) {
                final int taskId = i;
                executor.execute(() ->
                    System.out.println("Task " + taskId + " on " + Thread.currentThread().getName())
                );
            }
        } finally {
            executor.shutdown();
        }
    }
}
