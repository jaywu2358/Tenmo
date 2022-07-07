package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }


    //List all transfers
    @RequestMapping(path = "/transfers")
    public List<Transfer> list() {
        return transferDao.listAllTransfers();
    }

    //Get transfer details by transfer Id
    @RequestMapping(path = "/transfers/{id}")
    public Transfer get(@PathVariable int id) {
        return transferDao.getTransferDetailsById(id);
    }

    //Send transfer

}
