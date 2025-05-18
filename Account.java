import java.io.Serializable;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accountNumber;
    private String holderName;
    private double balance;
    private String password;

    public Account(String accountNumber, String holderName, double balance, String password) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.password = password;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Account No: " + accountNumber + ", Name: " + holderName + ", Balance: ₹" + balance;
    }
}
