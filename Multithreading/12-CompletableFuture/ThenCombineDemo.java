import java.util.concurrent.CompletableFuture;

public class ThenCombineDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> price = CompletableFuture.supplyAsync(() -> 100);
        CompletableFuture<Integer> shipping = CompletableFuture.supplyAsync(() -> 20);

        price.thenCombine(shipping, Integer::sum)
             .thenAccept(total -> System.out.println("Total: " + total))
             .join();
    }
}
