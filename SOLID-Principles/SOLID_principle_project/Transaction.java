public class Transaction {
    private final String transactionId;
    private final PaymentType paymentType;
    private final double amount;
    private PayymentStatus status;
    public Transaction(String transactionId, PaymentType paymentType, double amount) {
        this.transactionId = transactionId;
        this.paymentType = paymentType;
        this.amount = amount;
        this.status = PayymentStatus.PENDING;
    }
    public String getTransactionId() {
        return transactionId;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public double getAmount() {
        return amount;
    }

    public PayymentStatus getStatus() {
        return status;
    }

    public void setStatus(PayymentStatus status) {
        this.status = status;
    }

    public void display() {
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Payment Type: " + paymentType);
        System.out.println("Amount: " + amount);
        System.out.println("Status: " + status);
    }
}
