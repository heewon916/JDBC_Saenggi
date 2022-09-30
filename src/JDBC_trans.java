import java.sql.*;

public class JDBC_trans {
    /**
     * Insert and assign skills to a specific candidates
     * @param firstName
     * @param lastName
     * @param dob
     * @param email
     * @param phone
     * @param skills
     */
    public static void addCandidate(String firstName, String lastName, Date dob,
                                    String email, String phone, int[] skills){
        Connection conn = null;

        // for insert a new candidate
        PreparedStatement pstmt = null;

        // for assign skills to candidate
        PreparedStatement pstmtAssignment = null;

        // for getting candidate id
        ResultSet rs = null;

        try{
            conn = JDBC_Util.getConnection();

            // set auto commit to false
            conn.setAutoCommit(false);

            // insert candidate
            String sqlInsert = "insert into candidates(first_name, last_name, dob, phone, email) " +
                    "values(?, ?, ?, ?, ?);";
            pstmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setDate(3, dob);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);

            // get candidate ID
            rs = pstmt.getGeneratedKeys();
            int candidateID = 0;
            if (rs.next())
                candidateID = rs.getInt(1);

            int rowAffected = pstmt.executeUpdate();
            // 그 수정된 row에 대해서!! 연관된 테이블도 수정해줘야 하니깐
            if (rowAffected == 1){
                // assign skills to candidates
                String sqlPivot = "insert into candidate_skills(candidate_id, skill_id) " +
                        "values(?, ?);";
                pstmtAssignment = conn.prepareStatement(sqlPivot);
                for(int skillId : skills){
                    pstmtAssignment.setInt(1, candidateID);
                    pstmtAssignment.setInt(2, skillId);

                    pstmtAssignment.executeUpdate();
                    // 하는 이유가 뭐람?
                }
                conn.commit();
            } else{
                conn.rollback();
            }
        } catch(SQLException ex){
            // roll back the transaction
            try{
                if(conn != null){
                    conn.rollback();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        } finally {
            try{
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
                if(pstmtAssignment != null)
                    pstmtAssignment.close();
                if(conn != null) conn.close();
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static void main(String[] args){
        int[] skills = {1,2,3};
        addCandidate("John", "Doe", Date.valueOf("1991-01-04"),
                "john.d@yahoo.com", "(408)-898-5641", skills);
    }
}
