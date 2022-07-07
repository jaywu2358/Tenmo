package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.InsufficientFundsException;
import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
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

    @Override
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

    @Override
    public BigDecimal getBalanceByAccountId(int accountId) {

        String sql = "SELECT balance FROM account WHERE account_id = ?;";

        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);

        if (balance != null){
            return balance;
        } else {
            return BigDecimal.valueOf(-1);
        }
    }

    @Override
    public BigDecimal getBalanceByUserId(int userId) {
        String sql = "SELECT balance FROM account WHERE user_id = ?;";

        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);

        System.out.println(balance);

        if (balance != null){
            return balance;
        } else {
            return BigDecimal.valueOf(-1);
        }

//        balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
//
//        if(balance != null){
//            return balance;
//        } else {
//            return BigDecimal.valueOf(-1);
//        }

    }

    @Override
    public void addToBalance(int accountId, BigDecimal amountReceived) {

        String sql = "UPDATE account " +
                    "SET balance = balance + ? " +
                    "WHERE account_id = ?;";
        jdbcTemplate.update(sql, amountReceived, accountId);

    }

    @Override
    public void subtractFromBalance(int accountId, BigDecimal amountToSend) throws InsufficientFundsException {

        if (getBalanceByAccountId(accountId).compareTo(amountToSend) > 0) {
            String sql = "UPDATE account " +
                    "SET balance = balance - ? " +
                    "WHERE account_id = ?;";
            jdbcTemplate.update(sql, amountToSend, accountId);
        } else {
            throw new InsufficientFundsException();
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
