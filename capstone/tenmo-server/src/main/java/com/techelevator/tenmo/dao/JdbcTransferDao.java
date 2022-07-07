package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements  TransferDao {

    private JdbcTemplate jdbcTemplate;

//    @Override
//    public List<Transfer> listTransfersByAccount(int accountId) {
//        List<Transfer> transfers = new ArrayList<>();
//        String sql = "SELECT * FROM transfer JOIN ";
//
//        return new ArrayList<>();
//    }

    @Override
    public Transfer getTransferDetailsById(int transferId) {
        Transfer transfer = null;
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,transferId);
        if(results.next()){
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public List<Transfer> listAllTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }

//    @Override
//    public List<Transfer> listPendingTransfers(int userId) {
//        return new ArrayList<>();
//    }

    @Override
    public Transfer sendTransfer(Transfer transfer) {
        return null;
    }

//    @Override
//    public Transfer requestTransfer(Transfer transfer) {
//        return null;
//    }

//    @Override
//    public void approveTransfer(boolean isTransferApproved, int TransferId) {
//
//    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();

        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setTransferTypeId(rowSet.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        transfer.setAccountFromId(rowSet.getInt("account_from"));
        transfer.setAccountToId(rowSet.getInt("account_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));

        return transfer;
    }

}
