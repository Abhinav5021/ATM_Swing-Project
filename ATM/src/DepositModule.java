import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class DepositModule extends JFrame implements ActionListener {

    private JTextField amountField;
    private JButton depositBtn, backBtn;
    private int id;
    private int balance;

    public DepositModule(int id, int balance) {
        this.id = id;
        this.balance = balance;

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

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == depositBtn) {

            try {
                int amount = Integer.parseInt(amountField.getText());

                if(amount <= 0)
                    throw new Exception("Enter valid amount!");

                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/atm","root","9309");

                con.setAutoCommit(false);

                String updateSql = "UPDATE users SET balance = balance + ? WHERE id=?";
                PreparedStatement pst1 = con.prepareStatement(updateSql);
                pst1.setInt(1, amount);
                pst1.setInt(2, id);
                pst1.executeUpdate();

                String historySql = "INSERT INTO transactions(sender_id,receiver_id,amount) VALUES(NULL,?,?)";
                PreparedStatement pst2 = con.prepareStatement(historySql);
                pst2.setInt(1,id);
                pst2.setInt(2,amount);
                pst2.executeUpdate();

                con.commit();

                balance += amount;
                JOptionPane.showMessageDialog(this,
                        "Deposit Successful!\nUpdated Balance: ₹" + balance);

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
