package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    List<Transfer> listTransfersByAccount(int accountId);
    // Should this take in an account object instead?

    Transfer getTransferById(int transferId);

    List<Transfer> listAllTransfers();

    List<Transfer> listPendingTransfers();

    Transfer sendTransfer(Transfer transfer);

    Transfer requestTransfer(Transfer transfer);

    void approveTransfer(boolean isTransferApproved, int TransferId);
    // Might want to think through this method

}
