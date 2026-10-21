public  class BkashPayment implements PaymentMethod {
    @Override
    public PaymentResult processPayment(double amount) {
        
        

        if (amount>0) {
            return PaymentResult.success(amount);
        } else {
            return PaymentResult.failed(amount, "Bkash payment failed");
        }
    }
    
}
