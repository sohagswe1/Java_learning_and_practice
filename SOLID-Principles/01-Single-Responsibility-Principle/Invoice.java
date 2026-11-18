import java.util.List;

public class Invoice {
    private final List<Double> prices;

    public Invoice(List<Double> prices) {
        this.prices = List.copyOf(prices);
    }

    public double calculateTotal() {
        return prices.stream().mapToDouble(Double::doubleValue).sum();
    }
}
