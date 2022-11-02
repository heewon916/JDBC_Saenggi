package com.example.testapp1.jdbc.account;

import com.example.testapp1.jdbc.user.User;
import com.example.testapp1.jdbc.user.UserJdbc;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

public class AccountJdbcTest extends TestCase {
    AccountJdbc accountJdbc = new AccountJdbc();
    int isIncome = 1;
    int money = 500;

    public void testSaveAccount() { accountJdbc.saveAccount(account);}

    public void testUpdateBalance() {accountJdbc.updateBalance(account, isIncome, money);}
}