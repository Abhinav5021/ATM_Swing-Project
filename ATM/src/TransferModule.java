import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class TransferModule extends JFrame implements ActionListener {

    private JTextField receiverCardField, amountField;
    private JButton sendBtn, backBtn;
    private int senderId;

    public TransferModule(int senderId) {
        this.senderId = senderId;

        setTitle("Online Transfer");
        setSize(420, 300);
        setLayout(null);

        JLabel lbl1 = new JLabel("Receiver Card Number:");
        lbl1.setBounds(40, 50, 160, 30);
        add(lbl1);

        receiverCardField = new JTextField();
        receiverCardField.setBounds(220, 50, 150, 30);
        add(receiverCardField);

        JLabel lbl2 = new JLabel("Amount:");
        lbl2.setBounds(40, 100, 160, 30);
        add(lbl2);

        amountField = new JTextField();
        amountField.setBounds(220, 100, 150, 30);
        add(amountField);

        sendBtn = new JButton("Send Money");
        sendBtn.setBounds(80, 170, 120, 30);
        sendBtn.addActionListener(this);
        add(sendBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(230, 170, 100, 30);
        backBtn.addActionListener(this);
        add(backBtn);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == sendBtn) {

            try {
                String receiverCard = receiverCardField.getText().trim();
                int amount = Integer.parseInt(amountField.getText());

                if(!receiverCard.matches("\\d{10}"))
                    throw new Exception("Receiver Card must be 10 digits!");

                if(amount <= 0)
                    throw new Exception("Enter valid amount!");

                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/atm","root","9309");

                con.setAutoCommit(false);

                // Fetch sender balance
                String senderSql = "SELECT balance FROM users WHERE id=?";
                PreparedStatement pst1 = con.prepareStatement(senderSql);
                pst1.setInt(1,senderId);
                ResultSet rs1 = pst1.executeQuery();

                if(!rs1.next()) throw new Exception("Sender not found!");

                int senderBalance = rs1.getInt("balance");
                if(senderBalance < amount)
                    throw new Exception("Insufficient Balance!");

                // Fetch receiver ID
                String recvSql = "SELECT id FROM users WHERE card_number=?";
                PreparedStatement pst2 = con.prepareStatement(recvSql);
                pst2.setString(1,receiverCard);
                ResultSet rs2 = pst2.executeQuery();

                if(!rs2.next()) throw new Exception("Receiver not found!");

                int receiverId = rs2.getInt("id");

                // Debit sender
                String debitSql = "UPDATE users SET balance = balance - ? WHERE id=?";
                PreparedStatement pst3 = con.prepareStatement(debitSql);
                pst3.setInt(1,amount);
                pst3.setInt(2,senderId);
                pst3.executeUpdate();

                // Credit receiver
                String creditSql = "UPDATE users SET balance = balance + ? WHERE id=?";
                PreparedStatement pst4 = con.prepareStatement(creditSql);
                pst4.setInt(1,amount);
                pst4.setInt(2,receiverId);
                pst4.executeUpdate();

                // Insert history
                String historySql = "INSERT INTO transactions(sender_id,receiver_id,amount) VALUES(?,?,?)";
                PreparedStatement pst5 = con.prepareStatement(historySql);
                pst5.setInt(1,senderId);
                pst5.setInt(2,receiverId);
                pst5.setInt(3,amount);
                pst5.executeUpdate();

                con.commit();

                JOptionPane.showMessageDialog(this,"Transfer Successful!");
                con.close();
                dispose();

            } catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }

        if(e.getSource() == backBtn){
            dispose();
        }
    }
}
