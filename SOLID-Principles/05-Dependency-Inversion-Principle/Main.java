public class Main {
    public static void main(String[] args) {
        NotificationService emailNotification =
            new NotificationService(new EmailSender());
        emailNotification.notifyUser("Welcome to our application");

        NotificationService smsNotification =
            new NotificationService(new SmsSender());
        smsNotification.notifyUser("Your OTP is 123456");
    }
}
