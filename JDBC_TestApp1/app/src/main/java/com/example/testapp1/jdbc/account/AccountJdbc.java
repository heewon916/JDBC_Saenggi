package com.example.testapp1.jdbc.account;

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

public class AccountJdbc {
    // -> 김희원
    // 계좌 저장; ID값 리턴(계좌아이디)
    public int saveAccount(Account account){
        //db 접근 설정정보///////////////////////////////////////////////////
        String db_url = "jdbc:mysql://localhost:3306/saengji";
        String db_user = "root";
        String db_password = "hkim916!@";
        ///////////////////////////////////////////////////////////////////

        String sql = "INSERT INTO account(accountNum, bank, amount, checkCredit, userId, mainAmount) "+
                "VALUES(?, ?, ?, ?, ?, ?)";
        Resultset rs = null;
        int result = 0;

        try{
            Connection conn = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, account.getAmountNum());
            pstmt.setString(2, account.getBank());
            pstmt.setInt(3, account.getAmount());
            pstmt.setString(4, amount.getCheckCredit());
            pstmt.setInt(5, amount.getUserId());
            pstmt.setInt(6, amount.getMainAmount());

            int rowAffected = pstmt.executeUpdate();
            if(rowAffected == 1){
                // get ID
                rs = pstmt.getGenerateKeys();
                if(rs.next()){
                    result = rs.getInt(1);
                }
            }
            return result;

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }finally{
            try{
                if(rs!=null) rs.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    // -> 김희원
    // 잔액 수정
    // 반영 성공시 1, 반영 실패시 0
    public int updateBalance(Account account, int isIncome, int money){
        //db 접근 설정정보///////////////////////////////////////////////////
        String db_url = "jdbc:mysql://localhost:3306/saengji";
        String db_user = "root";
        String db_password = "hkim916!@";
        ///////////////////////////////////////////////////////////////////
        Statement stmt = null;

        ResultSet rs = null;

        Account account = new Account();

        int result = 0;

        int accountID = account.getID();

        int accountAmount = account.getAmount();

        int sign = (int)(Math.pow(-1, isIncome + 1));

        String sql = "UPDATE account " +
                "SET amount = ? " +
                " WHERE ID = " + accountID;

        try {
            Connection conn = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, (accountAmount + sign * money));
            System.out.println(pstmt);

            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1){
                rs = pstmt.getGeneratedKeys();
                if(rs.next()){
                    result = rs.getInt(1);
                }
            }
            return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

