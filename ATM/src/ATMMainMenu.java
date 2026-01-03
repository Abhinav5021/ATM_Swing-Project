import javax.swing.*;
import java.awt.event.*;

public class ATMMainMenu extends JFrame implements ActionListener {

    private JButton withdrawBtn, depositBtn, balanceBtn, pinChangeBtn, exitBtn;
    private int id;
    private String name;
    private int balance;

    public ATMMainMenu(int id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;

        setTitle("ATM Dashboard");
        setSize(400, 300);
        setLayout(null);

        JLabel welcome = new JLabel("Welcome, " + name);
        welcome.setBounds(120, 10, 200, 30);
        add(welcome);

        withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(50, 50, 120, 30);
        withdrawBtn.addActionListener(this);
        add(withdrawBtn);

        depositBtn = new JButton("Deposit");
        depositBtn.setBounds(200, 50, 120, 30);
        depositBtn.addActionListener(this);
        add(depositBtn);

        balanceBtn = new JButton("Balance Inquiry");
        balanceBtn.setBounds(50, 100, 270, 30);
        balanceBtn.addActionListener(this);
        add(balanceBtn);

        pinChangeBtn = new JButton("Change PIN");
        pinChangeBtn.setBounds(50, 150, 120, 30);
        pinChangeBtn.addActionListener(this);
        add(pinChangeBtn);

        exitBtn = new JButton("Exit");
        exitBtn.setBounds(200, 150, 120, 30);
        exitBtn.addActionListener(this);
        add(exitBtn);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == withdrawBtn) {
            new WithdrawModule(id, balance);
        } 
        else if(e.getSource() == depositBtn) {
            new DepositModule(id, balance);
        } 
        else if(e.getSource() == balanceBtn) {
            new BalanceModule(id);
        } 
        else if(e.getSource() == pinChangeBtn) {
            new PinChangeModule(id);
        } 
        else if(e.getSource() == exitBtn) {
            JOptionPane.showMessageDialog(this, "Thank you for using ATM!");
            System.exit(0);
        }
    }
}
