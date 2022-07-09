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
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if(results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public Account getAccountByUserId(int userId) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if(results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public List<Account> listAllAccounts() {
        List<Account> accounts = new ArrayList<>();

        String sql = "SELECT account_id, user_id, balance FROM account;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()){
            accounts.add(mapRowToAccount(results));
        }

        return accounts;
    }

    @Override
    public Account getAccountByUsername(String username) {

        Account account = null;
        String sql = "SELECT a.account_id, a.user_id, a.balance FROM account a JOIN tenmo_user ts ON a.user_id = ts.user_id WHERE ts.username = ?; ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);

        if (results.next()) {
            account = mapRowToAccount(results);
        }
        return account;

    }

    @Override
    public void addToBalance(Account recipientAccount, BigDecimal amountReceived) {

        String sql = "UPDATE account " +
                    "SET balance = balance + ? " +
                    "WHERE account_id = ?;";
        jdbcTemplate.update(sql, amountReceived, recipientAccount.getAccountId());

    }

    @Override
    public void subtractFromBalance(Account senderAccount, BigDecimal amountToSend) throws InsufficientFundsException {

        BigDecimal accountBalance = senderAccount.getBalance();

        if (accountBalance.compareTo(amountToSend) >= 0) {
            String sql = "UPDATE account " +
                    "SET balance = balance - ? " +
                    "WHERE account_id = ?;";
            jdbcTemplate.update(sql, amountToSend, senderAccount.getAccountId());
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
