import java.sql.Connection;
import java.sql.SQLException;

public class JDBC_con4 {
    public static void main(String[] args) {
        // create a new connection from MySQLJDBCUtil
        try (Connection conn = JDBC_Util.getConnection()) {

            // print out a message
            System.out.println(String.format("Connected to database %s "
                    + "successfully.", conn.getCatalog()));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}