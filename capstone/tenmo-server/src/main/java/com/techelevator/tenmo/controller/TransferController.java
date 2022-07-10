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

    private final TransferDao transferDao;
    private final AccountDao accountDao;

    private final int TYPE_REQUEST = 1;
    private final int TYPE_SEND = 2;
    private final int STATUS_PENDING = 1;
    private final int STATUS_APPROVED = 2;
    private final int STATUS_REJECTED = 3;

    public TransferController(TransferDao transferDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    //List all transfers
    @RequestMapping(path = "transfers")
    public List<Transfer> listTransfers(@RequestParam int userId, @RequestParam (required = false, defaultValue = "0")
            int transferTypeId, @RequestParam (required = false, defaultValue = "0") int transferStatusId) {
        if (transferStatusId == 0 && transferTypeId == 0) {
            return transferDao.listAllTransfers(userId);
        } else if (transferTypeId > 0 && transferStatusId == 0) {
            return transferDao.filterTransfersByType(userId, transferTypeId);
        } else if (transferTypeId == 0 && transferStatusId > 0) {
            return transferDao.filterTransfersByStatus(userId, transferStatusId);
        } else {
            return transferDao.filterTransfersByTypeAndStatus(userId, transferTypeId, transferStatusId);
        }
    }

    //Get transfer details by transfer Id
    @RequestMapping(path = "transfers/{id}")
    public Transfer get(@PathVariable int id) {
        return transferDao.getTransferById(id);
    }

    //Send transfer
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "transfers", method = RequestMethod.POST)
    public Transfer createTransfer(@RequestBody Transfer transfer) throws InsufficientFundsException {

        int senderAccountId = transfer.getAccountFromId();
        Account senderAccount = accountDao.getAccountByUserId(senderAccountId);

        int recipientAccountId = transfer.getAccountToId();
        Account recipientAccount = accountDao.getAccountByUserId(recipientAccountId);

        if (transfer.getTransferStatusId() == STATUS_APPROVED) {
            accountDao.subtractFromBalance(senderAccount, transfer.getAmount());
            accountDao.addToBalance(recipientAccount, transfer.getAmount());
        }

        transfer.setAccountFromId(senderAccount.getAccountId());
        transfer.setAccountToId(recipientAccount.getAccountId());

        transfer = transferDao.createTransfer(transfer);

        return transfer;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "transfers", method = RequestMethod.PUT)
    public void updateTransfer(@RequestBody Transfer transfer) throws InsufficientFundsException {

        Account senderAccount = accountDao.getAccountByAccountId(transfer.getAccountFromId());
        Account recipientAccount = accountDao.getAccountByAccountId(transfer.getAccountToId());

        if (transfer.getTransferStatusId() == STATUS_APPROVED) {
            accountDao.subtractFromBalance(senderAccount, transfer.getAmount());
            accountDao.addToBalance(recipientAccount, transfer.getAmount());
        }

        transferDao.updateTransferStatus(transfer);
    }

}
