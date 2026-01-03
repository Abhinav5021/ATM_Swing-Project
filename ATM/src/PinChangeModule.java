import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class PinChangeModule extends JFrame implements ActionListener {

    private JPasswordField oldPinField, newPinField, confirmPinField;
    private JButton changeBtn, backBtn;
    private int id;

    public PinChangeModule(int id) {
        this.id = id;

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

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == changeBtn) {

            try {
                String oldPin = new String(oldPinField.getPassword()).trim();
                String newPin = new String(newPinField.getPassword()).trim();
                String confirmPin = new String(confirmPinField.getPassword()).trim();

                if(oldPin.isEmpty() || newPin.isEmpty() || confirmPin.isEmpty())
                    throw new Exception("All fields are required!");

                if(!newPin.equals(confirmPin))
                    throw new Exception("New PIN and Confirm PIN do not match!");

                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/atm","root","9309");

                String verifySql = "SELECT pin FROM users WHERE id=?";
                PreparedStatement pst1 = con.prepareStatement(verifySql);
                pst1.setInt(1,id);
                ResultSet rs = pst1.executeQuery();

                if(!rs.next())
                    throw new Exception("Invalid Card/User ID!");

                if(!rs.getString("pin").equals(oldPin))
                    throw new Exception("Incorrect Old PIN!");

                String updateSql = "UPDATE users SET pin=? WHERE id=?";
                PreparedStatement pst2 = con.prepareStatement(updateSql);
                pst2.setString(1,newPin);
                pst2.setInt(2,id);
                pst2.executeUpdate();

                JOptionPane.showMessageDialog(this,"PIN Changed Successfully!");

                con.close();
                dispose();

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
