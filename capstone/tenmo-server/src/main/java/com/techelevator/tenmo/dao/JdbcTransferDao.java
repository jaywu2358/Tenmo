package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements  TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
    public List<Transfer> listAllTransfers(int userId) {
        //List<Transfer> transfers = new ArrayList<>();
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT t.transfer_id, tu.username, t.amount FROM transfer t " +
                "JOIN account a ON a.account_id = t.account_from JOIN tenmo_user tu ON a.user_id = tu.user_id " +
                "WHERE t.account_from = ? OR t.account_to = ? RETURNING transfer_id; ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
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
    public Transfer sendTransfer(BigDecimal amountToTransfer, int senderId, int recipientId) {
        Transfer transfer = null;
        String sql = "INSERT INTO transfer_type(transfer_type_desc) " +
                    "VALUES('Send') RETURNING transfer_type_id; ";

        Integer transferTypeId = jdbcTemplate.queryForObject(sql, Integer.class);

        sql = "INSERT INTO transfer_status(transfer_status_desc) " +
                "VALUES('Approved') RETURNING transfer_status_id; ";

        Integer transferStatusId = jdbcTemplate.queryForObject(sql, Integer.class);


        sql = "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES(?,?,?,?,?) RETURNING transfer_id; ";

        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, transferTypeId, transferStatusId, senderId, recipientId, amountToTransfer);

        return getTransferDetailsById(transferId);
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

//    private Transfer toStringViewTransfer(SqlRowSet rowSet)

//    private List<String> viewTransfer(Transfer newTransfer) {
//        List<>
//
//    }

}
