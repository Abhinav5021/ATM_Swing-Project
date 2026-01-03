import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class BalanceModule extends JFrame implements ActionListener {

    private JLabel balanceLabel;
    private JButton backBtn;
    private int id;

    public BalanceModule(int id) {
        this.id = id;

        setTitle("Balance Inquiry");
        setSize(400, 200);
        setLayout(null);

        balanceLabel = new JLabel();
        balanceLabel.setBounds(60, 50, 300, 30);
        add(balanceLabel);

        backBtn = new JButton("Back to Menu");
        backBtn.setBounds(120, 100, 150, 30);
        backBtn.addActionListener(this);
        add(backBtn);

        fetchBalance();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchBalance() {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/atm","root","9309");

            String sql = "SELECT balance FROM users WHERE id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                balanceLabel.setText("Your Current Balance: ₹ " + rs.getInt("balance"));
            }

            con.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backBtn){
            dispose();
        }
    }
}
