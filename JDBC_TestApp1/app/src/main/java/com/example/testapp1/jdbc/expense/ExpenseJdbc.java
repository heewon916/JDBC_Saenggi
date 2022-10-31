package com.example.testapp1.jdbc.expense;

import static java.sql.Date.valueOf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ExpenseJdbc {
    public int saveExpense(Expense expense){
        //db 접근 설정정보///////////////////////////////////////////////////
        String db_url = "jdbc:mysql://localhost:3306/saengji";
        String db_user = "root";
        String db_password = "Njs07088@";
        ///////////////////////////////////////////////////////////////////

        // for insert a new expense
        ResultSet rs = null;
        int result = 0;

        String sql = "INSERT INTO expense(userId, expense, expenseDate, content, category, accountId) "+
                "VALUES(?,?,?,?,?,?)";

        try{
            Connection conn = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, expense.getUserId());
            pstmt.setInt(2, expense.getExpense());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setDate(3,valueOf(df.format(expense.getExpenseDate())));
            pstmt.setString(4,expense.getContent());
            pstmt.setString(5,expense.getCategory());
            pstmt.setInt(6,expense.getAccountId());

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

}
