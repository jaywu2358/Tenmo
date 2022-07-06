package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements  TransferDao {

    @Override
    public List<Transfer> listTransfersByAccount(int accountId) {
        return new ArrayList<>();
    }

    @Override
    public Transfer getTransferById(int transferId) {
        return null;
    }

    @Override
    public List<Transfer> listAllTransfers() {
        return null;
    }

    @Override
    public List<Transfer> listPendingTransfers() {
        return new ArrayList<>();
    }

    @Override
    public Transfer sendTransfer(Transfer transfer) {
        return null;
    }

    @Override
    public Transfer requestTransfer(Transfer transfer) {
        return null;
    }

    @Override
    public void approveTransfer(boolean isTransferApproved, int TransferId) {

    }

}
