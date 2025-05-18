import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BankOperations {
    private Map<String, Account> accounts;
    private final String FILE_NAME = "accounts.dat";

    public BankOperations() {
        accounts = loadAccounts();
    }

    public boolean createAccount(String accNo, String name, double initialDeposit, String password) {
        if (!accounts.containsKey(accNo)) {
            accounts.put(accNo, new Account(accNo, name, initialDeposit, password));
            saveAccounts();
            return true;
        }
        return false;
    }

    public boolean deposit(String accNo, String password, double amount) {
        Account acc = accounts.get(accNo);
        if (acc != null && acc.getPassword().equals(password)) {
            acc.deposit(amount);
            saveAccounts();
            return true;
        }
        return false;
    }

    public boolean withdraw(String accNo, String password, double amount) {
        Account acc = accounts.get(accNo);
        if (acc != null && acc.getPassword().equals(password)) {
            if (acc.withdraw(amount)) {
                saveAccounts();
                return true;
            }
        }
        return false;
    }

    public double getBalance(String accNo, String password) {
        Account acc = accounts.get(accNo);
        if (acc != null && acc.getPassword().equals(password)) {
            return acc.getBalance();
        }
        return -1;
    }

    public boolean deleteAccount(String accNo, String password) {
        Account acc = accounts.get(accNo);
        if (acc != null && acc.getPassword().equals(password)) {
            accounts.remove(accNo);
            saveAccounts();
            return true;
        }
        return false;
    }

    public boolean authenticate(String accNo, String password) {
        Account acc = accounts.get(accNo);
        return acc != null && acc.getPassword().equals(password);
    }

    public void showAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            for (Account acc : accounts.values()) {
                System.out.println(acc);
            }
        }
    }

    private void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.out.println("Failed to save accounts.");
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Account> loadAccounts() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                return (Map<String, Account>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load accounts.");
        }
        return new HashMap<>();
    }
}
