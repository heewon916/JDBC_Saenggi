import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC_con2 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mysqljdbc";
        String user = "root";
        String password = "hkim916!@";
//        Connection conn = null;
        try(Connection conn = DriverManager.getConnection(url, user, password)) {

            // processing here
            System.out.println("JDBC_con2.java Success");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}