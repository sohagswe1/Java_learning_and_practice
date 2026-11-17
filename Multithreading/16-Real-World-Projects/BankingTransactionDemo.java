public class BankingTransactionDemo {
    static class BankAccount {
        private int balance = 1000;

        synchronized void withdraw(int amount, String user) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println(user + " withdrew " + amount + "; balance=" + balance);
            } else {
                System.out.println(user + " could not withdraw; insufficient funds");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount();
        Thread a = new Thread(() -> account.withdraw(700, "User-A"));
        Thread b = new Thread(() -> account.withdraw(500, "User-B"));
        a.start(); b.start(); a.join(); b.join();
        System.out.println("Final balance: " + account.balance);
    }
}
