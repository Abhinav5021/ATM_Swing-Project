import javax.swing.*;
import java.awt.event.*;

public class BalanceModule extends JFrame implements ActionListener {
    private JLabel balanceLabel;
    private JButton backBtn;

    // For demo, we’ll use a hardcoded balance.
    // Later, you can pass this from your BankAccount object or database.
    private double balance = 1500.00;

    public BalanceModule() {
        setTitle("Balance Inquiry");
        setSize(400, 200);
        setLayout(null);

        balanceLabel = new JLabel("Your Current Balance: ₹" + balance);
        balanceLabel.setBounds(50, 50, 300, 30);
        add(balanceLabel);

        backBtn = new JButton("Back to Menu");
        backBtn.setBounds(120, 100, 150, 30);
        backBtn.addActionListener(this);
        add(backBtn);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backBtn) {
            // Close this window and go back to Main Menu
            new ATMMainMenu();
            dispose();
        }
    }

    public static void main(String[] args) {
        new BalanceModule();
    }
}