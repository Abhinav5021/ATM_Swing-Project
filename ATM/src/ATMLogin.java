import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ATMLogin extends JFrame implements ActionListener {

    private JTextField cardField;
    private JPasswordField pinField;
    private JButton loginBtn;

    public ATMLogin() {

        setTitle("ATM Login");
        setSize(400, 250);
        setLayout(null);

        JLabel cardLabel = new JLabel("Card Number:");
        cardLabel.setBounds(50, 50, 120, 30);
        add(cardLabel);

        cardField = new JTextField();
        cardField.setBounds(180, 50, 150, 30);
        add(cardField);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(50, 100, 120, 30);
        add(pinLabel);

        pinField = new JPasswordField();
        pinField.setBounds(180, 100, 150, 30);
        add(pinField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(140, 150, 100, 30);
        loginBtn.addActionListener(this);
        add(loginBtn);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String card = cardField.getText().trim();
        String pin  = new String(pinField.getPassword()).trim();

        try {
            if(card.isEmpty() || pin.isEmpty())
                throw new Exception("Please enter Card Number and PIN!");

            if(!card.matches("\\d{10}"))
                throw new Exception("Card Number must be exactly 10 digits!");

            if(!pin.matches("\\d{4}"))
                throw new Exception("PIN must be exactly 4 digits!");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/atm","root","9309");

            String query = "SELECT id, name, balance FROM users WHERE card_number=? AND pin=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, card);
            pst.setString(2, pin);

            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int balance = rs.getInt("balance");

                JOptionPane.showMessageDialog(this,"Login Successful!");
                new ATMMainMenu(id, name, balance);
                dispose();
            }
            else{
                throw new Exception("Invalid Card Number or PIN!");
            }

            con.close();

        } 
        catch(SQLException ex){
            JOptionPane.showMessageDialog(this,"Database Error: " + ex.getMessage());
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ATMLogin();
    }
}
