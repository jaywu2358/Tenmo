package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

//    List<Transfer> listTransfersByAccount(int accountId);
//    // Should this take in an account object instead?

    Transfer getTransferDetailsById(int transferId);

    List<Transfer> listAllTransfers(int userId, int transferStatusId);
    List<Transfer> listAllTransfers(int userId);
    String getTransferTypeDescById(int id);
    String getTransferStatusDescById(int id);
    Transfer createTransfer(Transfer transfer);

    //Use for approving or rejecting a request transfer
    //we don't need to use the two methods below
    void updateTransferStatus(Transfer transfer);


}
