interface Payment{
    void pay(double amount);
}

class UPIPayment implements Payment{
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using UPI.");
    }
}

class CardPayment implements Payment{
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Credit Card.");
    }
}

class DebitCardPayment implements Payment{
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Debit Card.");
    }
}

class cashPayment implements Payment{
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Cash.");
    }
}
public class Payments{
    public static void main(String[] args) {
        Payment cashPayment = new cashPayment();
        Payment upiPayment = new UPIPayment();
        Payment cardPayment = new CardPayment();
        Payment debitCardPayment = new DebitCardPayment();

        cashPayment.pay(500);
        upiPayment.pay(1000.0);
        cardPayment.pay(2000.0);
        debitCardPayment.pay(1500.0);
    }
}