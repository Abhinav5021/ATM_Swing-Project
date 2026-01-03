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

                if(amount <= 0)
                    throw new Exception("Enter valid amount!");

                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/atm","root","9309");

                con.setAutoCommit(false);

                String balSql = "SELECT balance FROM users WHERE id=?";
                PreparedStatement pst1 = con.prepareStatement(balSql);
                pst1.setInt(1,id);
                ResultSet rs = pst1.executeQuery();

                if(!rs.next()) throw new Exception("Account not found!");

                int dbBalance = rs.getInt("balance");

                if(dbBalance < amount)
                    throw new Exception("Insufficient Balance!");

                String updateSql = "UPDATE users SET balance = balance - ? WHERE id=?";
                PreparedStatement pst2 = con.prepareStatement(updateSql);
                pst2.setInt(1,amount);
                pst2.setInt(2,id);
                pst2.executeUpdate();

                String historySql = "INSERT INTO transactions(sender_id,receiver_id,amount) VALUES(?,NULL,?)";
                PreparedStatement pst3 = con.prepareStatement(historySql);
                pst3.setInt(1,id);
                pst3.setInt(2,amount);
                pst3.executeUpdate();

                con.commit();

                JOptionPane.showMessageDialog(this,
                        "Withdrawal Successful!\nRemaining Balance: ₹" + (dbBalance - amount));

                con.close();

            } catch(SQLException ex){
                JOptionPane.showMessageDialog(this,"Database Error: " + ex.getMessage());
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }

        if(e.getSource() == backBtn){
            dispose();
        }
    }
}
