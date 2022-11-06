package main.java.incomeExpense;

import main.java.Jdbc_Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

public class IncomeJdbc {
//    static int userId;
//    static String Today;
    // Income 추가
    public static void addIncome(Integer userId, Integer income, Date incomeDate,
                                 String content, String category, Integer accountId){

        String sqlInsert_income = "INSERT INTO saengji.income(userId, income, incomeDate, content, category, accountId)"
                + "VALUES(?,?,?,?,?,?)";

        Connection conn2 = null;

        try (Connection conn = Jdbc_Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlInsert_income);
        ){

            conn.setAutoCommit(false);

            conn2 = conn;

            pstmt.setInt(1, userId);
            pstmt.setInt(2, income);
            pstmt.setDate(3, incomeDate);
            pstmt.setString(4, content);
            pstmt.setString(5, category);
            pstmt.setInt(6, accountId);

            int rowAffected = pstmt.executeUpdate();

            if(rowAffected == 1) {
                conn.commit();
            }
            else {
                conn.rollback();
            }

        } catch (SQLException ex) {
            try{
                if(conn2 != null)

                    conn2.rollback();

            }catch(SQLException e){
                System.out.println(e.getMessage());
            }

            System.out.println(ex.getMessage());
        }
    }
    //월간 수입 합 조회
    public static void listMonthIncome(int userId, String Today) {
        StringTokenizer std = new StringTokenizer(Today, "-");
        String Year = std.nextToken(); //2022
        String Month = std.nextToken(); //10
        int year = Integer.parseInt(Year);
        int month = Integer.parseInt(Month);

        int last_day;
        if(month %2 ==0 && month!=8) {
            last_day = 30;
        }
        else {
            last_day = 31;
        }

        //2월일 경우 (윤년인지 아닌지) 한 번 더 검사
        if(month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                last_day = 29;
            } else {
                last_day = 28;
            }
        }
        String Day = Integer.toString(last_day);
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/saengji";
            String user = "root";
            String password = "비밀번호";
            conn = DriverManager.getConnection(url, user, password);

//	           System.out.println("DB에 성공적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // 월간 수입 조회 sql 쿼리 문
        String sql = "SELECT SUM(income) AS sum_income FROM income"+
                "WHERE userId = "+userId+ "AND incomeDate >='"+Year+"-"+Month+"-01'"
                + " AND expenseDate <= '"+Year+"-"+Month+"-"+Day+"'";
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            BigInteger exp = new BigInteger(rs.getString(1));

        }catch(Exception e) {
            e.printStackTrace();

        }finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    //연간 수입 합 조회
    public static void listYearIncome(int userId, String Today) {
        StringTokenizer std = new StringTokenizer(Today, "-");
        String Year = std.nextToken(); //2022
        String Month = std.nextToken(); //10
        int year = Integer.parseInt(Year);

        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/saengji";
            String user = "root";
            String password = "비밀번호";
            conn = DriverManager.getConnection(url, user, password);

//	           System.out.println("DB에 성공적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // 연간 수입 조회 sql 쿼리 문
        String sql = "SELECT SUM(income) AS sum_income FROM income"+
                "WHERE userId = "+userId+ "AND incomeDate >='"+Year+"-01-01'"
                + " AND expenseDate <= '"+Year+"-12-31'";
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            BigInteger exp = new BigInteger(rs.getString(1));

        }catch(Exception e) {
            e.printStackTrace();

        }finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
