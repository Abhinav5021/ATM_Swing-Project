import javax.swing.*;
import java.awt.event.*;

public class ATMMainMenu extends JFrame implements ActionListener {
    private JButton withdrawBtn, depositBtn, balanceBtn, pinChangeBtn, exitBtn;

    public ATMMainMenu() {
        setTitle("ATM Main Menu");
        setSize(400, 300);
        setLayout(null);

        // Withdraw Button
        withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(50, 50, 120, 30);
        withdrawBtn.addActionListener(this);
        add(withdrawBtn);

        // Deposit Button
        depositBtn = new JButton("Deposit");
        depositBtn.setBounds(200, 50, 120, 30);
        depositBtn.addActionListener(this);
        add(depositBtn);

        // Balance Inquiry Button
        balanceBtn = new JButton("Balance Inquiry");
        balanceBtn.setBounds(50, 100, 270, 30);
        balanceBtn.addActionListener(this);
        add(balanceBtn);

        // Change PIN Button
        pinChangeBtn = new JButton("Change PIN");
        pinChangeBtn.setBounds(50, 150, 120, 30);
        pinChangeBtn.addActionListener(this);
        add(pinChangeBtn);

        // Exit Button
        exitBtn = new JButton("Exit");
        exitBtn.setBounds(200, 150, 120, 30);
        exitBtn.addActionListener(this);
        add(exitBtn);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == withdrawBtn) {
            JOptionPane.showMessageDialog(this, "Withdraw Module will open here.");
            // new WithdrawModule();
        } else if(e.getSource() == depositBtn) {
            JOptionPane.showMessageDialog(this, "Deposit Module will open here.");
            // new DepositModule();
        } else if(e.getSource() == balanceBtn) {
            JOptionPane.showMessageDialog(this, "Balance Inquiry Module will open here.");
            // new BalanceModule();
        } else if(e.getSource() == pinChangeBtn) {
            JOptionPane.showMessageDialog(this, "PIN Change Module will open here.");
            // new PinChangeModule();
        } else if(e.getSource() == exitBtn) {
            JOptionPane.showMessageDialog(this, "Thank you for using ATM!");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new ATMMainMenu();
    }
}