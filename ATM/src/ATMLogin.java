import javax.swing.*;
import java.awt.event.*;

public class ATMLogin extends JFrame implements ActionListener {
    private JTextField cardField;
    private JPasswordField pinField;
    private JButton loginBtn;

    public ATMLogin() {
        setTitle("ATM Login");
        setSize(400, 250);
        setLayout(null);

        JLabel cardLabel = new JLabel("Card Number:");
        cardLabel.setBounds(50, 50, 100, 30);
        add(cardLabel);

        cardField = new JTextField();
        cardField.setBounds(160, 50, 150, 30);
        add(cardField);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(50, 100, 100, 30);
        add(pinLabel);

        pinField = new JPasswordField();
        pinField.setBounds(160, 100, 150, 30);
        add(pinField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(130, 150, 100, 30);
        loginBtn.addActionListener(this);
        add(loginBtn);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cardNumber = cardField.getText();
        String pin = new String(pinField.getPassword());

        // Hardcoded validation for demo
        if (cardNumber.equals("123456") && pin.equals("1234")) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            // Here you can open the ATM Menu window
            new ATMMainMenu(); // Example: call your main menu class
            dispose(); // close login window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Card Number or PIN");
        }
    }

    
    public static void main(String[] args) {
        new ATMLogin();
    }
}
