public class NagadPayment implements PaymentMethod {
    @Override
    public PaymentResult processPayment(double amount) {
        if (amount > 0) {
            return PaymentResult.success(amount);
        } else {
            return PaymentResult.failed(amount, "Nagad payment failed");
        }
    }
    
}
