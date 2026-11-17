import java.util.concurrent.*;

public class CallableFutureDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Callable<Integer> task = () -> 20 + 22;
            Future<Integer> future = executor.submit(task);
            System.out.println("Result: " + future.get());
        } finally {
            executor.shutdown();
        }
    }
}
