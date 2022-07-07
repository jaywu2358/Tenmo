package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

//    List<Transfer> listTransfersByAccount(int accountId);
//    // Should this take in an account object instead?

    Transfer getTransferDetailsById(int transferId);

    List<Transfer> listAllTransfers(int userId);

//    List<Transfer> listPendingTransfers();

    Transfer sendTransfer(Transfer transfer);

//    Transfer requestTransfer(BigDecimal amountToRequest);

//    void approveTransfer(boolean isTransferApproved, int TransferId);
//    // Might want to think through this method

}
