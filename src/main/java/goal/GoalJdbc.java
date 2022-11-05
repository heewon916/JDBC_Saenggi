package main.java.goal;

import main.java.Jdbc_Util;
import main.java.goal.Goal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static java.sql.Date.valueOf;
// --> 추가
import java.sql.ResultSetMetaData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// --> 추가
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class GoalJdbc {

    public int saveGoal(Goal goal){
        ResultSet rs = null;
        int result = 0;

        String sql = "INSERT INTO goal(userId, goalAmount, goalStartDate, GoalEndDate) "+
                "VALUES(?,?,?,?)";

        try{
            Connection conn = Jdbc_Util.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, goal.getUserId());
            pstmt.setInt(2, goal.getGoalAmount());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setDate(3, valueOf(df.format(goal.getGoalStartDate())));
            pstmt.setDate(4, valueOf(df.format(goal.getGoalEndDate())));

            int rowAffected = pstmt.executeUpdate();
            if(rowAffected==1){
                //get user id
                rs=pstmt.getGeneratedKeys();
                if(rs.next()){
                    result=rs.getInt(1);
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(rs!=null) rs.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

        return result;
    }

    //1 반환시 목표 달성 성공, 0 반환시 목표 달성실패
    public int achieveGoal(int goalId){
        // for select a user
        String sql = "SELECT * "+
                "FROM goal "+
                "WHERE ID="+goalId+"";

        Statement stmt = null;
        ResultSet rs = null;
        try {
            Connection conn = Jdbc_Util.getConnection();
            stmt  = conn.createStatement();
            rs = stmt.executeQuery(sql);
            Goal goal = new Goal();
            while(rs.next()) {
                goal.setID(goalId);
                goal.setUserId(rs.getInt("userId"));
                goal.setGoalAmount(rs.getInt("goalAmount"));
                goal.setGoalStartDate(rs.getDate("goalStartDate"));
                goal.setGoalEndDate(rs.getDate("goalEndDate"));
            }

            //goalPeriod내에 목표한 금액만큼 모았는지 확인//////////////////
            String sql2 = "SELECT sum(income), userId, incomeDate " +
                    "from income " +
                    "where incomeDate>='" + goal.getGoalStartDate() + "' " +
                    "and incomeDate<= '" + goal.getGoalEndDate() + "' " +
                    "and userId=" + goal.getUserId();

            stmt  = conn.createStatement();
            rs = stmt.executeQuery(sql2);
            int incomeAmount = 0;
            while(rs.next()) {
                incomeAmount = rs.getInt("sum(income)");
            }
            //////////////////////////////////////////////////////////
            if(incomeAmount>=goal.getGoalAmount()){
                return 1;
            }else{
                return 0;
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    // -> 김희원
    // 목표 조회
    // 목표를 리스트 형식으로 출력
    // 20221104 -> 해당 userId를 이용해 목표설정여부랑 goal 내용 return
    public int checkGoalExist(ResultSet rs) throws SQLException {
        if (rs.next()) {
            rs.previous();
            return 1;
        } else {
            return 0;
        }
    }

    public JSONArray listGoal(int userId) {
        // for insert a new user
        ResultSet rs = null;
        Statement stmt = null;

        String sql = "SELECT * from GOAL where userId = " + userId;
        JSONArray jsonArray = new JSONArray();
        try {
            Connection conn = Jdbc_Util.getConnection();
            Statement st = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            rs = st.executeQuery(sql);
            //stmt = conn.createStatement();
            //rs = stmt.executeQuery(sql);

            //System.out.println("checkGoalExist(rs) = " + checkGoalExist(rs));
            if (checkGoalExist(rs) == 1) {
                //System.out.println("get in");
                JSONObject obj = new JSONObject();
                ResultSetMetaData rsmd = rs.getMetaData();
                int total_rows = rsmd.getColumnCount();
                //System.out.println(rsmd.getColumnLabel(1));
                //System.out.println(total_rows);
                while (rs.next()) {
                    for (int i = 0; i < total_rows; i++) {
                        String columnName = rsmd.getColumnName(i + 1);
                        Object columnValue = rs.getObject(i + 1);
                        obj.put(columnName, columnValue);
                        //System.out.println(obj);
                    }
                    jsonArray.add(obj);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return jsonArray;
    }
}