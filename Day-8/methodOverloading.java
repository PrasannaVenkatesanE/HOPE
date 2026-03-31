class Withdraw{
    void withdraw(int amount){
        System.out.println("Withdrawed "+amount);
    }
    void withdraw(double amount){
        System.out.println("Withdrawed "+amount);
    }
    void withdraw(double amount, String currency){
        System.out.println("Withdrawed "+amount+" in "+currency);
    }
}

public class methodOverloading{
    public static void main(String[] args){
        Withdraw w = new Withdraw();
        w.withdraw(100);
        w.withdraw(100.000);
        w.withdraw(100,"USD");
        w.withdraw(100,"rupees");
    }
}