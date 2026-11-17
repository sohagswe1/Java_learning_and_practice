import java.util.concurrent.*;

public class FutureStatusDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Future<String> future = executor.submit(() -> {
                Thread.sleep(200);
                return "Completed";
            });

            System.out.println("Done initially? " + future.isDone());
            System.out.println("Result: " + future.get());
            System.out.println("Done finally? " + future.isDone());
        } finally {
            executor.shutdown();
        }
    }
}
