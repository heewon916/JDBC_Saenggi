
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC_con1 {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/mysqljdbc";
            String user = "root";
            String password = "hkim916!@";

            conn = DriverManager.getConnection(url, user, password);

            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}