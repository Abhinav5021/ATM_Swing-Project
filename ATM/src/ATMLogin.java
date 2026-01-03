import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ATMLogin extends JFrame implements ActionListener {

    private JTextField cardField;
    private JPasswordField pinField;
    private JButton loginBtn;

    public ATMLogin() {

        setTitle("ATM Login");
        setSize(420, 300);
        setLayout(new BorderLayout());

        // ===== Header =====
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 102, 204));
        JLabel title = new JLabel("ATM LOGIN");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(title);

        // ===== Center Panel =====
        JPanel center = new JPanel(new GridBagLayout());
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel cardLabel = new JLabel("Card Number");
        cardLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        center.add(cardLabel, gbc);

        cardField = new JTextField();
        cardField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.ipadx = 120;
        center.add(cardField, gbc);

        JLabel pinLabel = new JLabel("PIN");
        pinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        center.add(pinLabel, gbc);

        pinField = new JPasswordField();
        pinField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 1;
        center.add(pinField, gbc);

        loginBtn = new JButton("LOGIN");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setBackground(new Color(0, 102, 204));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.addActionListener(this);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.ipady = 8;
        center.add(loginBtn, gbc);

        add(header, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String card = cardField.getText().trim();
        String pin  = new String(pinField.getPassword()).trim();

        try {
            if(card.isEmpty() || pin.isEmpty())
                throw new Exception("Please enter Card Number and PIN");

            if(!card.matches("\\d{10}"))
                throw new Exception("Card Number must be 10 digits");

            if(!pin.matches("\\d{4}"))
                throw new Exception("PIN must be 4 digits");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/atm","root","9309");

            String sql = "SELECT id, name, balance FROM users WHERE card_number=? AND pin=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, card);
            pst.setString(2, pin);

            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new ATMMainMenu(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("balance")
                );
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Card Number or PIN");
            }

            con.close();

        } catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ATMLogin();
    }
}
