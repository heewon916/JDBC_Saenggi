package com.example.testapp1.jdbc.goal;

import static java.sql.Date.valueOf;

import com.example.testapp1.jdbc.user.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GoalJdbc {
    public int saveGoal(Goal goal){
        //db 접근 설정정보///////////////////////////////////////////////////
        String db_url = "jdbc:mysql://localhost:3306/saengji";
        String db_user = "root";
        String db_password = "hkim916!@";
        ///////////////////////////////////////////////////////////////////

        // for insert a new user
        ResultSet rs = null;
        int result = 0;

        String sql = "INSERT INTO goal(userId, goalAmount, goalPeriod) "+
                "VALUES(?,?,?)";

        try{
            Connection conn = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, goal.getUserId());
            pstmt.setInt(2, goal.getGoalAmount());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setDate(3, valueOf(df.format(goal.getGoalPeriod())));

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
        //db 접근 설정정보///////////////////////////////////////////////////
        String db_url = "jdbc:mysql://localhost:3306/saengji";
        String db_user = "root";
        String db_password = "Njs07088@";
        ///////////////////////////////////////////////////////////////////

        // for select a user
        String sql = "SELECT * "+
                "FROM goal "+
                "WHERE ID="+goalId+"";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(db_url, db_user, db_password);
            stmt  = conn.createStatement();
            rs = stmt.executeQuery(sql);
            Goal goal = new Goal();
            while(rs.next()) {
                goal.setID(goalId);
                goal.setUserId(rs.getInt("userId"));
                goal.setGoalAmount(rs.getInt("goalAmount"));
                goal.setGoalPeriod(rs.getDate("goalPeriod"));
            }

            //goalPeriod내에 목표한 금액만큼 모았는지 확인//////////////////
            String sql2 = "SELECT sum(income) " +
                    "from income " +
                    "where incomeDate<=" + goal.getGoalPeriod();

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

    // 목표를 리스트 형식으로 출력
    public void listGoal(){
        //db 접근 설정정보///////////////////////////////////////////////////
        String db_url = "jdbc:mysql://localhost:3306/saengji";
        String db_user = "root";
        String db_password = "hkim916!@";
        ///////////////////////////////////////////////////////////////////

        // for insert a new user
        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;

        String sql = "SELECT * from GOAL";

        try {
            conn = DriverManager.getConnection(db_url, db_user, db_password);
            stmt  = conn.createStatement();
            rs = stmt.executeQuery(sql);
            // Goal goal = new Goal();

            System.out.println("ID \t userId \t goalAmount \t goalPeriod ");
            while(rs.next()) {
                System.out.println(rs.getInt("ID") + "\t" + rs.getInt("userId") + "\t"
                                + rs.getInt("goalAmount") + "\t" + rs.getDate("goalPeriod") + "\t"
                                    );
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
    }

}
