public class PremiumDiscount implements Discount {
    @Override
    public double apply(double amount) {
        return amount * 0.15;
    }
}
