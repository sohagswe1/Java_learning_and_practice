import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanDemo {
    public static void main(String[] args) {
        AtomicBoolean started = new AtomicBoolean(false);
        if (started.compareAndSet(false, true)) {
            System.out.println("This thread changed the state from false to true");
        }
    }
}
