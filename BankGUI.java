import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankGUI {
    private JFrame frame;
    private BankOperations bank;

    public BankGUI() {
        bank = new BankOperations();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Bank Management System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 1, 10, 10));

        JButton createBtn = new JButton("Create Account");
        JButton loginBtn = new JButton("Login to Account");
        JButton showAllBtn = new JButton("Show All Accounts");
        JButton exitBtn = new JButton("Exit");

        createBtn.addActionListener(e -> showCreateAccountForm());
        loginBtn.addActionListener(e -> showLoginForm());
        showAllBtn.addActionListener(e -> bank.showAllAccounts());
        exitBtn.addActionListener(e -> System.exit(0));

        frame.add(createBtn);
        frame.add(loginBtn);
        frame.add(showAllBtn);
        frame.add(exitBtn);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showCreateAccountForm() {
        JFrame createFrame = new JFrame("Create Account");
        createFrame.setSize(350, 300);
        createFrame.setLayout(new GridLayout(5, 2, 5, 5));

        JTextField accField = new JTextField();
        JTextField nameField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JTextField depositField = new JTextField();
        JButton submitBtn = new JButton("Create");

        createFrame.add(new JLabel("Account No:"));
        createFrame.add(accField);
        createFrame.add(new JLabel("Name:"));
        createFrame.add(nameField);
        createFrame.add(new JLabel("Password:"));
        createFrame.add(passField);
        createFrame.add(new JLabel("Initial Deposit:"));
        createFrame.add(depositField);
        createFrame.add(new JLabel()); // Empty cell for spacing
        createFrame.add(submitBtn);

        submitBtn.addActionListener(e -> {
            String accNo = accField.getText().trim();
            String name = nameField.getText().trim();
            String password = new String(passField.getPassword()).trim();
            String depositStr = depositField.getText().trim();

            if (accNo.isEmpty() || name.isEmpty() || password.isEmpty() || depositStr.isEmpty()) {
                JOptionPane.showMessageDialog(createFrame, "All fields must be filled.");
                return;
            }

            try {
                double deposit = Double.parseDouble(depositStr);
                bank.createAccount(accNo, name, deposit, password);
                JOptionPane.showMessageDialog(createFrame, "Account created!");
                createFrame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(createFrame, "Enter a valid numeric deposit.");
            }
        });

        createFrame.setLocationRelativeTo(null);
        createFrame.setVisible(true);
    }

    private void showLoginForm() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 200);
        loginFrame.setLayout(new GridLayout(3, 2, 5, 5));

        JTextField accField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        loginFrame.add(new JLabel("Account No:"));
        loginFrame.add(accField);
        loginFrame.add(new JLabel("Password:"));
        loginFrame.add(passField);
        loginFrame.add(new JLabel());
        loginFrame.add(loginBtn);

        loginBtn.addActionListener(e -> {
            String accNo = accField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (accNo.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginFrame, "Enter both account number and password.");
                return;
            }

            if (bank.authenticate(accNo, password)) {
                loginFrame.dispose();
                showDashboard(accNo, password);
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials.");
            }
        });

        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    private void showDashboard(String accNo, String password) {
        JFrame dashFrame = new JFrame("Account Dashboard");
        dashFrame.setSize(300, 300);
        dashFrame.setLayout(new GridLayout(5, 1, 10, 10));

        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton balanceBtn = new JButton("Show Balance");
        JButton deleteBtn = new JButton("Delete Account");
        JButton logoutBtn = new JButton("Logout");

        depositBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(dashFrame, "Enter deposit amount:");
            if (input != null) {
                try {
                    double amount = Double.parseDouble(input);
                    bank.deposit(accNo, password, amount);
                    JOptionPane.showMessageDialog(dashFrame, "Deposit successful.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dashFrame, "Invalid amount.");
                }
            }
        });

        withdrawBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(dashFrame, "Enter withdrawal amount:");
            if (input != null) {
                try {
                    double amount = Double.parseDouble(input);
                    bank.withdraw(accNo, password, amount);
                    JOptionPane.showMessageDialog(dashFrame, "Withdrawal successful.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dashFrame, "Invalid amount.");
                }
            }
        });

        balanceBtn.addActionListener(e -> {
            double bal = bank.getBalance(accNo, password);
            JOptionPane.showMessageDialog(dashFrame, "Current Balance: ₹" + bal);
        });

        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(dashFrame, "Are you sure?", "Delete Account", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                bank.deleteAccount(accNo, password);
                JOptionPane.showMessageDialog(dashFrame, "Account deleted.");
                dashFrame.dispose();
            }
        });

        logoutBtn.addActionListener(e -> dashFrame.dispose());

        dashFrame.add(depositBtn);
        dashFrame.add(withdrawBtn);
        dashFrame.add(balanceBtn);
        dashFrame.add(deleteBtn);
        dashFrame.add(logoutBtn);

        dashFrame.setLocationRelativeTo(null);
        dashFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankGUI::new);
    }
}
