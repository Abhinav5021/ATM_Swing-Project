import javax.swing.*;
import java.awt.event.*;

public class PinChangeModule extends JFrame implements ActionListener {
    private JPasswordField oldPinField, newPinField, confirmPinField;
    private JButton changeBtn, backBtn;

    // Hardcoded PIN for demo
    private String currentPin = "1234";

    public PinChangeModule() {
        setTitle("Change PIN");
        setSize(400, 300);
        setLayout(null);

        JLabel oldPinLabel = new JLabel("Enter Old PIN:");
        oldPinLabel.setBounds(50, 50, 150, 30);
        add(oldPinLabel);

        oldPinField = new JPasswordField();
        oldPinField.setBounds(200, 50, 120, 30);
        add(oldPinField);

        JLabel newPinLabel = new JLabel("Enter New PIN:");
        newPinLabel.setBounds(50, 100, 150, 30);
        add(newPinLabel);

        newPinField = new JPasswordField();
        newPinField.setBounds(200, 100, 120, 30);
        add(newPinField);

        JLabel confirmPinLabel = new JLabel("Confirm New PIN:");
        confirmPinLabel.setBounds(50, 150, 150, 30);
        add(confirmPinLabel);

        confirmPinField = new JPasswordField();
        confirmPinField.setBounds(200, 150, 120, 30);
        add(confirmPinField);

        changeBtn = new JButton("Change PIN");
        changeBtn.setBounds(80, 200, 120, 30);
        changeBtn.addActionListener(this);
        add(changeBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(220, 200, 100, 30);
        backBtn.addActionListener(this);
        add(backBtn);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == changeBtn) {
            String oldPin = new String(oldPinField.getPassword());
            String newPin = new String(newPinField.getPassword());
            String confirmPin = new String(confirmPinField.getPassword());

            if(!oldPin.equals(currentPin)) {
                JOptionPane.showMessageDialog(this, "Incorrect Old PIN!");
            } else if(newPin.isEmpty() || confirmPin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "New PIN fields cannot be empty!");
            } else if(!newPin.equals(confirmPin)) {
                JOptionPane.showMessageDialog(this, "New PIN and Confirm PIN do not match!");
            } else {
                currentPin = newPin; // update hardcoded PIN
                JOptionPane.showMessageDialog(this, "PIN Changed Successfully!\nYour new PIN is: " + currentPin);
            }
        } else if(e.getSource() == backBtn) {
            JOptionPane.showMessageDialog(this, "Returning to Main Menu...");
            new ATMMainMenu(); // call your main menu class
            dispose();
        }
    }

    public static void main(String[] args) {
        new PinChangeModule();
    }
}