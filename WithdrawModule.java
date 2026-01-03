import javax.swing.*;
import java.awt.event.*;

public class WithdrawModule extends JFrame implements ActionListener {
    private JTextField amountField;
    private JButton withdrawBtn, backBtn;

    // Hardcoded balance for demo
    private double balance = 1500.00;

    public WithdrawModule() {
        setTitle("Withdraw Money");
        setSize(400, 250);
        setLayout(null);

        JLabel amountLabel = new JLabel("Enter Amount to Withdraw:");
        amountLabel.setBounds(50, 50, 200, 30);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(220, 50, 100, 30);
        add(amountField);

        withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(80, 120, 100, 30);
        withdrawBtn.addActionListener(this);
        add(withdrawBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(200, 120, 100, 30);
        backBtn.addActionListener(this);
        add(backBtn);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == withdrawBtn) {
            try {
                double amount = Double.parseDouble(amountField.getText());

                if(amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Enter a valid amount!");
                } else if(amount > balance) {
                    JOptionPane.showMessageDialog(this, "Insufficient Balance!");
                } else {
                    balance -= amount; // update hardcoded balance
                    JOptionPane.showMessageDialog(this, 
                        "Withdrawal Successful!\nRemaining Balance: ₹" + balance);
                }
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a numeric value.");
            }
        } else if(e.getSource() == backBtn) {
            JOptionPane.showMessageDialog(this, "Returning to Main Menu...");
            new ATMMainMenu(); // call your main menu class
            dispose();
        }
    }

    public static void main(String[] args) {
        new WithdrawModule();
    }
}