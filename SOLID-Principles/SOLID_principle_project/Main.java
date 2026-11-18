

public class Main {

    public static void main(String[] args) {

        PaymentMethod payment = new Cashondelivery();

        PaymentService paymentService = new PaymentService(payment);
        PaymentResult result = payment.processPayment(-1500);

        System.out.println("Status: " + result.getStatus());
        System.out.println("Amount: " + result.getAmount());
        System.out.println("Message: " + result.getMessage());
    }
}