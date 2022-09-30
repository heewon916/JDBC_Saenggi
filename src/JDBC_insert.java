import com.mysql.cj.protocol.Resultset;

import java.sql.*;

public class JDBC_insert {
    /**
     * Insert a new Candidate
     * @param firstName
     * @param lastName
     * @param dob
     * @param email
     * @param phone
     * @return
     */
    public static int insertCandidate(String firstName, String lastName, Date dob,
                                      String email, String phone){
        // for insert a new candidate
        ResultSet rs = null;
        int candidateId = 0;

        String sql = "insert into candidates(first_name, last_name, dob, phone, email) " +
                "values(?,?,?,?,?);";
        try(Connection conn = JDBC_Util.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            // set parameters for statement
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setDate(3, dob);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);

            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1){ // 정상 수행된 경우; 중복 데이터 발생하지 않은 것을 의미함
                // get candidate ID
                rs = pstmt.getGeneratedKeys(); // 어떤 키로 생성되었는지!!
                // i.e., on calling the next() method for the first time the result set pointer/cursor
                // will be moved to the 1st row (from default position).
                // that's why this if statement executed only once. because there won't be the 2nd row.
                if (rs.next()){
                    candidateId = rs.getInt(1);
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(rs != null) rs.close();
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return candidateId;
        // if return 0, rowAffected != 1

    }
    public static void main(String[] args){
        // insert a new candidate
        int id = insertCandidate("Bush", "Lily", Date.valueOf("1980-01-04"),
                "bush.l@yahoo.com", "(408) 898-6666");

        System.out.println(String.format("A new candidate with id %d has been inserted.", id));
    }
}
