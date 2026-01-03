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

                if(amount <= 0){
                    JOptionPane.showMessageDialog(this,"Enter valid amount!");
                    return;
                }

                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/atm","root","9309");

                String sql = "UPDATE users SET balance = balance + ? WHERE id = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, amount);
                pst.setInt(2, id);
                pst.executeUpdate();

                balance += amount;

                JOptionPane.showMessageDialog(this,
                        "Deposit Successful!\nUpdated Balance: ₹" + balance);

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
