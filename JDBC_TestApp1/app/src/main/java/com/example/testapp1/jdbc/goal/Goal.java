package com.example.testapp1.jdbc.goal;

import java.util.Date;

public class Goal {
    private int ID;
    private int userId;
    private int goalAmount;
    private Date goalStartDate;
    private Date goalEndDate;

    //constructor
    public Goal(){

    }
    public Goal(int userId, int goalAmount, Date goalStartDate, Date goalEndDate) {
        this.userId = userId;
        this.goalAmount = goalAmount;
        this.goalStartDate = goalStartDate;
        this.goalEndDate = goalEndDate;
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

    public Date getGoalStartDate() {
        return goalStartDate;
    }

    public Date getGoalEndDate() {
        return goalEndDate;
    }

    public void setGoalStartDate(Date goalStartDate) {
        this.goalStartDate = goalStartDate;
    }

    public void setGoalEndDate(Date goalEndDate) {
        this.goalEndDate = goalEndDate;
    }
}
