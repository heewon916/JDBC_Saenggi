import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_query1 {
    public static void main(String[] args) {
        //        String sql = "SELECT first_name, last_name, email " +
        //                     "FROM candidates";
        // 이렇게 sql문을 적을 수도 있다.
        String sql = "SELECT first_name, last_name, email FROM candidates";

        try (Connection conn = JDBC_Util.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {
            System.out.println("연결 성공");
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("last_name") + "\t" +
                        rs.getString("first_name")  + "\t" +
                        rs.getString("email"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}