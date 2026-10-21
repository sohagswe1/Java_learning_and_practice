public class PaymentService {
   

    private final PaymentMethod paymentMethod;

    public PaymentService(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentResult processPayment(double amount) {

        if (amount <= 0) {
            return PaymentResult.failed(
                amount, "Invalid amount"
            );
        }

        return paymentMethod.processPayment(amount);
    

}
}
