package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.exception.InsufficientFundsException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }


    //List all transfers
    @RequestMapping(path = "transfers")
    public List<Transfer> listAllTransfersForAGivenUser(@RequestParam int userId) {
        return transferDao.listAllTransfers(userId);
    }

    //Get transfer details by transfer Id
    @RequestMapping(path = "transfers/{id}")
    public Transfer get(@PathVariable int id) {
        return transferDao.getTransferDetailsById(id);
    }

    //Send transfer
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "transfers", method = RequestMethod.POST)
    public Transfer sendTransfer(@RequestBody Transfer transfer) throws InsufficientFundsException {

        int senderAccountId = transfer.getAccountFromId();
        accountDao.subtractFromBalance(senderAccountId, transfer.getAmount());

        int recipientAccountId = transfer.getAccountToId();
        accountDao.addToBalance(recipientAccountId, transfer.getAmount());

        transfer = transferDao.createTransfer(transfer);

        return transfer;
    }

    @RequestMapping(path = "transfer/type")
    public String getTransferTypeDescById(@RequestParam int transferTypeId) {
        return transferDao.getTransferTypeDescById(transferTypeId);
    }


    @RequestMapping(path = "transfer/status")
    public String getTransferStatusDescById(@RequestParam int transferStatusId) {
        return transferDao.getTransferStatusDescById(transferStatusId);
    }

}
