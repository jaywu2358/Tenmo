package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    Account getAccountByAccountId(int accountId);

    Account getAccountByUserId(int userId);

    List<Account> listAllAccounts();

    BigDecimal getBalanceByAccountId(int accountId);

    BigDecimal getBalanceByUserId(int userId);

}
