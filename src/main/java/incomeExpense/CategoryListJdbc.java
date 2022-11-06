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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class CategoryListJdbc {
//    static int userId;
//    static String Today;

    // 일일 총지출, 총수입 합산 및 일일 카테고리별 합산
    public static List searchDaySum(Integer userId, Date expenseDate, Date incomeDate) {

        // 일일 총 지출 합산
        String sqlSUM_expense =
                "SELECT SUM(expense) as all_sum_expense " +
                        "FROM saengji.expense " +
                        "WHERE userId = ? && expenseDate=?;";
        // 일일 총 수입 합산
        String sqlSUM_income =
                "SELECT SUM(income) as all_sum_income " +
                        "FROM saengji.income " +
                        "WHERE userId = ? && incomeDate=?;";
        // 일일 지출 카테고리
        String sqlSELECT_e_category =
                "SELECT SUM(expense) as cate_sum_expense, category " +
                "FROM saengji.expense " +
                "WHERE userId = ? && expenseDate = ? " +
                "group by category;";
        // 일일 수입 카테고리
        String sqlSELECT_i_category =
                "SELECT SUM(income) as cate_sum_income, category " +
                        "FROM saengji.income " +
                        "WHERE userId = ? && incomeDate = ? " +
                        "group by category;";

        try (Connection conn = Jdbc_Util.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement(sqlSUM_expense);
             PreparedStatement pstmt2 = conn.prepareStatement(sqlSUM_income);
             PreparedStatement pstmt3 = conn.prepareStatement(sqlSELECT_e_category);
             PreparedStatement pstmt4 = conn.prepareStatement(sqlSELECT_i_category);
        ){

            pstmt1.setInt(1, userId);
            pstmt1.setDate(2, expenseDate);

            pstmt2.setInt(1, userId);
            pstmt2.setDate(2, incomeDate);

            pstmt3.setInt(1,userId);
            pstmt3.setDate(2, expenseDate);

            pstmt4.setInt(1,userId);
            pstmt4.setDate(2, incomeDate);

            ResultSet rs1 = pstmt1.executeQuery();
            ResultSet rs2 = pstmt2.executeQuery();
            ResultSet rs3 = pstmt3.executeQuery();
            ResultSet rs4 = pstmt4.executeQuery();

            List temp1 = ResultToList(rs1);
            List temp2 = ResultToList(rs2);
            List temp3 = ResultToList(rs3);
            List temp4 = ResultToList(rs4);

            List join_temp = new ArrayList();
            join_temp.addAll(temp1);
            join_temp.addAll(temp2);
            join_temp.addAll(temp3);
            join_temp.addAll(temp4);

            return join_temp;

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());

            return null;

        }


    }

    // 일일 지출, 수입 목록
    public static List searchDayList(Integer userId, Date expenseDate, Date incomeDate) {

        String sqlList_expense = "SELECT expense, expenseDate, content, category, bank FROM saengji.expense " +
                "INNER JOIN account ON expense.accountId = account.ID WHERE expense.userId = ? && expense.expenseDate = ?;";
        String sqlList_income = "SELECT income, incomeDate, content, category, bank FROM saengji.income " +
                "INNER JOIN account ON income.accountId = account.ID WHERE income.userId = ? && income.incomeDate = ?;";


        try (Connection conn = Jdbc_Util.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement(sqlList_expense);
             PreparedStatement pstmt2 = conn.prepareStatement(sqlList_income );
        ){

            pstmt1.setInt(1, userId);
            pstmt1.setDate(2, expenseDate);

            pstmt2.setInt(1, userId);
            pstmt2.setDate(2, incomeDate);

            ResultSet rs1 = pstmt1.executeQuery();
            ResultSet rs2 = pstmt2.executeQuery();

            List temp1 = ResultToList(rs1);
            List temp2 = ResultToList(rs2);

            List join_temp = new ArrayList();
            join_temp.addAll(temp1);
            join_temp.addAll(temp2);

            return join_temp;

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());

            return null;

        }

    }

    // ResultSet to List
    public static List<Map<String, Object>> ResultToList(ResultSet rs) throws SQLException {

        List<Map<String, Object>> result = new ArrayList<>();
        ResultSetMetaData temp_rs = (ResultSetMetaData) rs.getMetaData();
        int size_column = temp_rs.getColumnCount();

        while (rs.next()) {

            Map<String, Object> row = new HashMap<>();

            for (int i = 1; i <= size_column; i++) {

                String columnName = temp_rs.getColumnName(i);
                Object columnValue = rs.getObject(i);
                row.put(columnName, columnValue);

            }
            result.add(row);
        }
        return result;
    }

    //월간  지출, 수입 카테고리 별  조회
    public static List monthCategory(int userId, String Today) {
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

        // 월간 지출 조회 sql 쿼리 문
        String sql1 = "SELECT category, SUM(expense) as sum_expense FROM expense "+
                "WHERE userId = "+userId+ " AND expenseDate >='"+Year+"-"+Month+"-01'"
                + " AND expenseDate <= '"+Year+"-"+Month+"-"+Day+"' GROUP BY category";
        // 월간 수입 조회 sql 쿼리 문
        String sql2 = "SELECT category, SUM(income) as sum_income FROM income "+
                "WHERE userId = "+userId+ " AND incomeDate >='"+Year+"-"+Month+"-01'"
                + " AND incomeDate <= '"+Year+"-"+Month+"-"+Day+"' GROUP BY category";

        Statement st = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;

        try {
            st = conn.createStatement();
            rs1 = st.executeQuery(sql1);
            rs2 = st.executeQuery(sql2);

            List monthCategory = new ArrayList();

            List temp1 = ResultToList(rs1);
            List temp2 = ResultToList(rs2);

            monthCategory.addAll(temp1);
            monthCategory.addAll(temp2);

            return monthCategory;

        }catch(Exception e) {
            e.printStackTrace();
            return null;

        }finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    //연간 지출 합 조회
    public static List yearCategory(int userId, String Today) {
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

        // 연간 지출 조회 sql 쿼리 문
        String sql1 = "SELECT category, SUM(expense) as sum_expense FROM expense "+
                "WHERE userId = "+userId+ " AND expenseDate >='"+Year+"-01-01'"
                + " AND expenseDate <= '"+Year+"-12-31' GROUP BY category";

        // 연간 수입 조회 sql 쿼리 문
        String	sql2 = "SELECT category, SUM(income) as sum_income FROM income "+
                "WHERE userId = "+userId+ " AND incomeDate >='"+Year+"-01-01'"
                + " AND incomeDate <= '"+Year+"-12-31' GROUP BY category";

        Statement st = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;

        try {
            st = conn.createStatement();
            rs1 = st.executeQuery(sql1);
            rs2 = st.executeQuery(sql2);

            List yearCategory = new ArrayList();

            List temp1 = ResultToList(rs1);
            List temp2 = ResultToList(rs2);

            yearCategory.addAll(temp1);
            yearCategory.addAll(temp2);

            return yearCategory;

        }catch(Exception e) {
            e.printStackTrace();
            return null;

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
