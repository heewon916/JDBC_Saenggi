import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBC_query2 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String sqlSelect2 = "select phone " +
                "from candidates where last_name = ?;";
        try(Connection conn = JDBC_Util.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlSelect2)){
            // prepare data for Select2
            String lastName = "Lee";
            pstmt.setString(1, lastName);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                System.out.println(lastName + "의 phone: "+rs.getString("phone"));
            }
            while(!lastName.equals("end")){
                System.out.print("전화번호를 알고 싶은 사람의 last_name을 입력하세요:");
                lastName = sc.nextLine();
                pstmt.setString(1, lastName);
                rs = pstmt.executeQuery();
                while(rs.next()){
                    System.out.println(lastName + "의 phone: "+rs.getString("phone"));
                }
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
