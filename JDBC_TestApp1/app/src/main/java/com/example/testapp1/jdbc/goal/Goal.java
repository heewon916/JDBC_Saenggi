package com.example.testapp1.jdbc.goal;

import java.util.Date;

public class Goal {
    private int ID;
    private int userId;
    private int goalAmount;
    private Date goalPeriod;

    //constructor
    public Goal(){

    }
    public Goal(int userId, int goalAmount, Date goalPeriod) {
        this.userId = userId;
        this.goalAmount = goalAmount;
        this.goalPeriod = goalPeriod;
    }

    //getter, setter
    public void setID(int id) {
        ID = id;
    }

    public int getId() {
        return ID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(int goalAmount) {
        this.goalAmount = goalAmount;
    }

    public Date getGoalPeriod() {
        return goalPeriod;
    }

    public void setGoalPeriod(Date goalPeriod) {
        this.goalPeriod = goalPeriod;
    }
}
