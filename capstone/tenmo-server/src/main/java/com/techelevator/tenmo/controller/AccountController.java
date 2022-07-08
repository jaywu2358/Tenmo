package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    //List all users
    @RequestMapping(path = "users")
    public List<User> list() {
        return  userDao.findAll();
    }

    //Get account balance
    @RequestMapping(path = "balance")
    public BigDecimal getBalance(Principal principal) {
        return accountDao.getBalanceByUsername(principal.getName());
    }

    @RequestMapping(path = "account/{accountId}")
    public Account getAccountByAccountId(@PathVariable int accountId) {
        return accountDao.getAccountByAccountId(accountId);
    }

    @RequestMapping(path = "account/user/{userId}")
    public Account getAccountByUserId(@PathVariable int userId) {
        return accountDao.getAccountByUserId(userId);
    }

    @RequestMapping(path = "username")
    public String getUsernameByUserId(@RequestParam int userId) {
        return userDao.getUsernameByUserId(userId);
    }


}
