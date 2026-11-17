import java.util.concurrent.atomic.AtomicInteger;

public class CompareAndSetDemo {
    public static void main(String[] args) {
        AtomicInteger stock = new AtomicInteger(10);
        boolean updated = stock.compareAndSet(10, 9);
        System.out.println("Updated? " + updated);
        System.out.println("Stock: " + stock.get());
    }
}
