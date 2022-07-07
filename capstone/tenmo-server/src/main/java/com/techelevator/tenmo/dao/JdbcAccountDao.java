package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public Account getAccountByAccountId(int accountId) {
        Account account = null;
        String sql = "SELECT account_id FROM account WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if(results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }

//    public Account getAccountByUserId(int userId) {
//        Account account = null;
//        String sql = "SELECT account_id FROM account WHERE user_id = ?;";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
//        if(results.next()) {
//            account = mapRowToAccount(results);
//        }
//        return account;
//    }

    public List<Account> listAllAccounts() {
        List<Account> accounts = new ArrayList<>();

        //Update * to all column names
        String sql = "SELECT * FROM account;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()){
            accounts.add(mapRowToAccount(results));
        }

        return accounts;
    }

    public BigDecimal getBalanceByAccountId(int accountId) {

        String sql = "SELECT balance FROM account WHERE account_id = ?;";

        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);

        if(balance!= null){
            return balance;
        } else {
            return BigDecimal.valueOf(-1);
        }
    }

    public BigDecimal getBalanceByUserId(int userId) {
        String sql = "SELECT balance FROM account WHERE user_id = ?;";

        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);

        if(balance!= null){
            return balance;
        } else {
            return BigDecimal.valueOf(-1);
        }
    }

    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();

        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));

        return account;
    }

}
