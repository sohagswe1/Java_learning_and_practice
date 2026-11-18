public class DiscountCalculator {
    public double calculateFinalPrice(double amount, Discount discount) {
        return amount - discount.apply(amount);
    }
}
