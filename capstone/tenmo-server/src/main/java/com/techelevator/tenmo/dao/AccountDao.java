package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.InsufficientFundsException;
import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    Account getAccountByAccountId(int accountId);

    Account getAccountByUserId(int userId);

    List<Account> listAllAccounts();

    Account getAccountByUsername(String username);

    void addToBalance(int accountId, BigDecimal amountReceived);

    void subtractFromBalance(int accountId, BigDecimal amountToSend) throws InsufficientFundsException;

}
