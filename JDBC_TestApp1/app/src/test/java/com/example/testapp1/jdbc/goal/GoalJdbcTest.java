package com.example.testapp1.jdbc.goal;

import com.example.testapp1.jdbc.user.User;
import com.example.testapp1.jdbc.user.UserJdbc;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

public class GoalJdbcTest extends TestCase {
    GoalJdbc goalJdbc = new GoalJdbc();

    public void testSaveGoal() {
        Calendar cal = Calendar.getInstance();
        cal.set(2022,11,1);
        Date myGoalPeriod = cal.getTime();
        int result = goalJdbc.saveGoal(new Goal(1,100000, myGoalPeriod));
        System.out.println(result);
    }

    public void testAchieveGoal(){
        goalJdbc.achieveGoal(1);
    }

    public void testListGoal(){ goalJdbc.listGoal();}

}