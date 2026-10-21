public class PaymentResult {

    private final PayymentStatus status;
    private final double amount;
    private final String message;

    public PaymentResult(
            PayymentStatus status,
            double amount,
            String message) {
        this.status = status;
        this.amount = amount;
        this.message = message;
    }

    public PayymentStatus getStatus() {
        return status;
    }

    public double getAmount() {
        return amount;
    }

    public String getMessage() {
        return message;
    }

    public static PaymentResult success(double amount) {
        return new PaymentResult(
                PayymentStatus.SUCCESS,
                amount,
                "Payment successful"
        );
    }

    public static PaymentResult failed(double amount, String message) {
        return new PaymentResult(
                PayymentStatus.FAILED,
                amount,
                message
        );
    }
}
 
    

