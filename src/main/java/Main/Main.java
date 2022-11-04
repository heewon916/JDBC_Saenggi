package main.java.Main;

import main.java.account.Account;
import main.java.goal.Goal;
import main.java.goal.GoalJdbc;
import org.json.simple.JSONArray;

import java.sql.Date;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
//        LocalDate localDate = LocalDate.of(2022, 9, 11);
//        Date date = Date.valueOf(localDate);
        // Goal goal = new Goal(2, 1000, date, date);
        GoalJdbc goalJdbc = new GoalJdbc();
        // goalJdbc.saveGoal(goal);
        JSONArray jsonArray = goalJdbc.listGoal(2);
        System.out.println(jsonArray);


    }
}