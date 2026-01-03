import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ATMMainMenu extends JFrame implements ActionListener {

    private JButton withdrawBtn, depositBtn, balanceBtn, pinChangeBtn, transferBtn, exitBtn;
    private int id;
    private String name;

    public ATMMainMenu(int id, String name, int balance) {
        this.id = id;
        this.name = name;

        setTitle("ATM Dashboard");
        setSize(450, 380);
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(0,102,204));
        JLabel title = new JLabel("Welcome, " + name);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(title);

        // Center Panel
        JPanel center = new JPanel(new GridLayout(3,2,15,15));
        center.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        withdrawBtn = new JButton("Withdraw");
        depositBtn = new JButton("Deposit");
        balanceBtn = new JButton("Balance");
        pinChangeBtn = new JButton("Change PIN");
        transferBtn = new JButton("Online Transfer");
        exitBtn = new JButton("Exit");

        JButton[] buttons = {withdrawBtn, depositBtn, balanceBtn, pinChangeBtn, transferBtn, exitBtn};

        for(JButton b : buttons){
            b.setFont(new Font("Segoe UI", Font.BOLD, 14));
            b.setBackground(new Color(240,240,240));
            b.setFocusPainted(false);
            b.addActionListener(this);
            center.add(b);
        }

        add(header, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private int getLatestBalance() {
        int bal = 0;
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/atm","root","9309");

            PreparedStatement pst = con.prepareStatement("SELECT balance FROM users WHERE id=?");
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) bal = rs.getInt("balance");

            con.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return bal;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==withdrawBtn)
            new WithdrawModule(id, getLatestBalance());

        else if(e.getSource()==depositBtn)
            new DepositModule(id, getLatestBalance());

        else if(e.getSource()==balanceBtn)
            new BalanceModule(id);

        else if(e.getSource()==pinChangeBtn)
            new PinChangeModule(id);

        else if(e.getSource()==transferBtn)
            new TransferModule(id);

        else if(e.getSource()==exitBtn){
            JOptionPane.showMessageDialog(this,"Thank you for using ATM!");
            System.exit(0);
        }
    }
}
