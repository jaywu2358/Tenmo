package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

//    List<Transfer> listTransfersByAccount(int accountId);
//    // Should this take in an account object instead?

    Transfer getTransferById(int transferId);

    List<Transfer> filterTransfersByStatus(int userId, int transferStatusId);
    List<Transfer> filterTransfersByType(int userId, int transferTypeId);
    List<Transfer> filterTransfersByTypeAndStatus(int userId, int transferTypeId, int transferStatusId);
    List<Transfer> listAllTransfers(int userId);
    Transfer createTransfer(Transfer transfer);

    //Use for approving or rejecting a request transfer
    //we don't need to use the two methods below
    void updateTransferStatus(Transfer transfer);


}
