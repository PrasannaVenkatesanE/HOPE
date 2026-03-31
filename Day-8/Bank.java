class BankAccount{
    private double balance;
    private double account;
    void deposit(int amount){
        balance += amount;
        System.out.println("Rupees:"+amount +" is deposited to the bank");
        getBalance();
    }
    void withdraw(int amount){
        balance -= amount;
        System.out.println("Rupess:"+amount+" is withdrawed from the bank");
        getBalance();
    }
    void getBalance(){
        System.out.println("Account balance is :" + balance);
    }
}

public class Bank{
    public static void main(String[] args){
        BankAccount bank = new BankAccount();
        bank.deposit(1000);
        bank.withdraw(500);
    }
}