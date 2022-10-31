package com.example.testapp1.jdbc.user;

import static java.sql.Date.valueOf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class UserJdbc {
    public int saveUser(User user){
        //db 접근 설정정보///////////////////////////////////////////////////
        String db_url = "jdbc:mysql://localhost:3306/saengji";
        String db_user = "root";
        String db_password = "Njs07088@";
        ///////////////////////////////////////////////////////////////////

        // for insert a new user
        ResultSet rs = null;
        int result = 0;

        String sql = "INSERT INTO user(userId, userPw, phoneNumber, birthday, created, userName) "+
                "VALUES(?,?,?,?,?,?)";

        try{
            Connection conn = DriverManager.getConnection(db_url, db_user, db_password);
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getUserPw());
            pstmt.setString(3, user.getPhoneNumber());

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setDate(4, valueOf(df.format(user.getBirthday())));
            pstmt.setDate(5, valueOf(df.format(user.getCreated())));
            pstmt.setString(6, user.getUserName());

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

    public User login(String userId, String userPw){
        //db 접근 설정정보///////////////////////////////////////////////////
        String db_url = "jdbc:mysql://localhost:3306/saengji";
        String db_user = "root";
        String db_password = "Njs07088@";
        ///////////////////////////////////////////////////////////////////

        // for select a user
        String sql = "select * "+
                "From user "+
                "WHERE userId='"+userId+"' "+
                "AND userPw='"+userPw+"'";
        System.out.println(sql);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(db_url, db_user, db_password);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            User user = new User();
            while(rs.next()) {
                user.setUserId(rs.getString("userId"));
                user.setUserPw(rs.getString("userPw"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setBirthday(rs.getDate("birthday"));
                user.setCreated(rs.getDate("created"));
                user.setUserName(rs.getString("userName"));
            }
            return user;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
