public class RegularDiscount implements Discount {
    @Override
    public double apply(double amount) {
        return amount * 0.05;
    }
}
