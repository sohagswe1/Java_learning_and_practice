public class RocketPayment implements PaymentMethod {
    @Override
    public PaymentResult processPayment(double amount) {
        if (amount > 0) {
            return PaymentResult.success(amount);
        } else {
            return PaymentResult.failed(amount, "Rocket payment failed");
        }
    }
}
