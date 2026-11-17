import java.util.concurrent.CompletableFuture;

public class ExceptionallyDemo {
    public static void main(String[] args) {
        String result = CompletableFuture.supplyAsync(() -> {
            if (true) throw new RuntimeException("Something failed");
            return "Success";
        }).exceptionally(error -> "Fallback: " + error.getMessage()).join();

        System.out.println(result);
    }
}
