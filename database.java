import java.sql.*;
public class database {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/ATM";
        String user = "root";
        String password = "9309"; 

         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
             while (rs.next()) {
                System.out.println(
                    rs.getInt("card_number") + " " +
                    rs.getInt("pin")
                );
 }

            con.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
