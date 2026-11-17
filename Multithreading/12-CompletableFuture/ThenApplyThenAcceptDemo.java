import java.util.concurrent.CompletableFuture;

public class ThenApplyThenAcceptDemo {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> 21)
            .thenApply(value -> value * 2)
            .thenAccept(result -> System.out.println("Result: " + result))
            .join();
    }
}
