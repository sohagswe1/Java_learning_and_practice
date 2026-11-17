import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SubmitDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Future<?> future = executor.submit(() -> System.out.println("Task submitted"));
            future.get(); // Waits for task completion.
        } finally {
            executor.shutdown();
        }
    }
}
