package com.example.testapp1.jdbc.user;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

public class UserJdbcTest extends TestCase {
    public void testSaveUser() {
        UserJdbc userJdbc = new UserJdbc();
        Calendar cal = Calendar.getInstance();
        cal.set(2000,8,27);
        Date myBirthday = cal.getTime();
        int result = userJdbc.saveUser(new User("seoyoung1030","1234","010-2222-2222",myBirthday,new Date(), "ChoSeoyoung"));
        System.out.println(result);
    }

    public void testLogin() {
        UserJdbc userJdbc = new UserJdbc();
        User user = userJdbc.login("seoyoung","1234");
        System.out.println("userId: "+user.getUserId());
        System.out.println("userPw: "+user.getUserPw());
        System.out.println("PhoneNumber: "+user.getPhoneNumber());
        System.out.println("birthday: "+user.getBirthday());
        System.out.println("created: "+user.getCreated());
        System.out.println("userName: "+user.getUserName());
    }
}