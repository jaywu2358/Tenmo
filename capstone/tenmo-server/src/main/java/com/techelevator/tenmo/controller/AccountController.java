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

    @RequestMapping(path = "users")
    public List<User> listUsers() {
        return userDao.findAll();
    }

    @RequestMapping(path = "account")
    public Account getAccountByUsername(Principal principal) {
        return accountDao.getAccountByUsername(principal.getName());
    }

    @RequestMapping(path = "username")
    public String getUsernameByUserId(@RequestParam int userId) {
        return userDao.getUsernameByUserId(userId);
    }

}
