import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC_Update {
    /*
    Update candidate demo
     */
    public static void update(){
        String sqlUpdate = "update candidates " +
                "set last_name = ? " +
                "where id = ?;";
        try(Connection conn = JDBC_Util.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)){
            //prepare for update
            String lastname = "xxxxx";
            int id = 100;
            pstmt.setString(1, lastname);
            pstmt.setInt(2, id);

            int rowAffected = pstmt.executeUpdate();
            System.out.println(String.format("Row Affectd %d", rowAffected));
            //reuse the prepared statement
            lastname = "yyyyy";
            id = 101;
            pstmt.setString(1, lastname);
            pstmt.setInt(2, id);

            rowAffected = pstmt.executeUpdate();
            System.out.println(String.format("Row affected %d", rowAffected));

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args){
        update();
    }
}
