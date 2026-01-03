import java.sql.*;

public class database {

    
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/atm";
        String user = "root";
        String password = "9309";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MySQL successfully!");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + "  " +
                    rs.getString("card_number") + "  " +
                    rs.getString("name") + "  " +
                    rs.getInt("pin")+"  "+
                    rs.getInt("balance")
                );
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
