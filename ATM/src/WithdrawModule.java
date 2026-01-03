import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class WithdrawModule extends JFrame implements ActionListener {

    private JTextField amountField;
    private JButton withdrawBtn, backBtn;
    private int id;
    private int balance;

    public WithdrawModule(int id, int balance) {
        this.id = id;
        this.balance = balance;

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

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == withdrawBtn) {

            try {
                int amount = Integer.parseInt(amountField.getText());

                if(amount <= 0){
                    JOptionPane.showMessageDialog(this,"Enter valid amount!");
                    return;
                }

                if(amount > balance){
                    JOptionPane.showMessageDialog(this,"Insufficient Balance!");
                    return;
                }

                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/atm","root","9309");

                String sql = "UPDATE users SET balance = balance - ? WHERE id = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, amount);
                pst.setInt(2, id);
                pst.executeUpdate();

                balance -= amount;

                JOptionPane.showMessageDialog(this,
                        "Withdrawal Successful!\nRemaining Balance: ₹" + balance);

                con.close();

            } catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Enter numeric value only!");
            }
        }

        if(e.getSource() == backBtn){
            dispose();
        }
    }
}
