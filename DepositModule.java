import javax.swing.*;
import java.awt.event.*;

public class DepositModule extends JFrame implements ActionListener {
    private JTextField amountField;
    private JButton depositBtn, backBtn;

    // Hardcoded balance for demo
    private double balance = 1500.00;

    public DepositModule() {
        setTitle("Deposit Money");
        setSize(400, 250);
        setLayout(null);

        JLabel amountLabel = new JLabel("Enter Amount to Deposit:");
        amountLabel.setBounds(50, 50, 200, 30);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(220, 50, 100, 30);
        add(amountField);

        depositBtn = new JButton("Deposit");
        depositBtn.setBounds(80, 120, 100, 30);
        depositBtn.addActionListener(this);
        add(depositBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(200, 120, 100, 30);
        backBtn.addActionListener(this);
        add(backBtn);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == depositBtn) {
            try {
                double amount = Double.parseDouble(amountField.getText());

                if(amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Enter a valid amount!");
                } else {
                    balance += amount; // update hardcoded balance
                    JOptionPane.showMessageDialog(this, 
                        "Deposit Successful!\nUpdated Balance: ₹" + balance);
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
        new DepositModule();
    }
}