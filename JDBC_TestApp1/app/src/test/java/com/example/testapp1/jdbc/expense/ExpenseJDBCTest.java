package com.example.testapp1.jdbc.expense;

import com.example.testapp1.jdbc.user.User;
import com.example.testapp1.jdbc.user.UserJdbc;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

public class ExpenseJDBCTest extends TestCase {
    public void testSaveExpense() {
        ExpenseJdbc expenseJdbc = new ExpenseJdbc();
        Calendar cal = Calendar.getInstance();
        cal.set(2022,10,24);
        Date expenseDate = cal.getTime();
        int result = expenseJdbc.saveExpense(new Expense(1,20000,expenseDate,"shopping", 1));
        System.out.println(result);
    }
}