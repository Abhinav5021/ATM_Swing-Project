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

            String oldPin = new String(oldPinField.getPassword());
            String newPin = new String(newPinField.getPassword());
            String confirmPin = new String(confirmPinField.getPassword());

            if(oldPin.isEmpty() || newPin.isEmpty() || confirmPin.isEmpty()){
                JOptionPane.showMessageDialog(this,"All fields are required!");
                return;
            }

            if(!newPin.equals(confirmPin)){
                JOptionPane.showMessageDialog(this,"New PIN and Confirm PIN do not match!");
                return;
            }

            try {
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/atm","root","9309");

                // Verify old PIN
                String verifySql = "SELECT pin FROM users WHERE id=?";
                PreparedStatement pst1 = con.prepareStatement(verifySql);
                pst1.setInt(1,id);
                ResultSet rs = pst1.executeQuery();

                if(rs.next()){
                    if(!rs.getString("pin").equals(oldPin)){
                        JOptionPane.showMessageDialog(this,"Incorrect Old PIN!");
                        con.close();
                        return;
                    }
                }

                // Update new PIN
                String updateSql = "UPDATE users SET pin=? WHERE id=?";
                PreparedStatement pst2 = con.prepareStatement(updateSql);
                pst2.setString(1,newPin);
                pst2.setInt(2,id);
                pst2.executeUpdate();

                JOptionPane.showMessageDialog(this,"PIN Changed Successfully!");

                // Reload data and return to menu
                String reloadSql = "SELECT name, balance FROM users WHERE id=?";
                PreparedStatement pst3 = con.prepareStatement(reloadSql);
                pst3.setInt(1,id);
                ResultSet rs2 = pst3.executeQuery();

                if(rs2.next()){
                    new ATMMainMenu(id, rs2.getString("name"), rs2.getInt("balance"));
                }

                con.close();
                dispose();

            } catch(Exception ex){
                ex.printStackTrace();
            }
        }

        if(e.getSource() == backBtn){
            dispose();
        }
    }
}
