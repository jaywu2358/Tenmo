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
        String sql = "SELECT t.transfer_id, t.transfer_type_id, tt.transfer_type_desc, t.transfer_status_id, ts.transfer_status_desc, t.account_from, t.account_to, t.amount " +
                "FROM transfer t " +
                "JOIN transfer_type tt " +
                "ON tt.transfer_type_id = t.transfer_type_id " +
                "JOIN transfer_status ts " +
                "ON ts.transfer_status_id = t.transfer_status_id " +
                "WHERE t.transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public List<Transfer> listAllTransfers(int userId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT t.transfer_id, t.transfer_type_id, t.transfer_status_id, t.account_from, t.account_to, t.amount " +
                "FROM transfer t " +
                "JOIN account a " +
                "ON a.account_id = t.account_from OR a.account_id = t.account_to " +
                "WHERE a.user_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
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

        String sql = "INSERT INTO transfer_type(transfer_type_desc) " +
                    "VALUES('Send') RETURNING transfer_type_id;";

        int transferTypeId = jdbcTemplate.queryForObject(sql, Integer.class);

        sql = "SELECT transfer_type_desc FROM transfer_type WHERE transfer_type_id = ?;";

        transfer.setTransferTypeMessage(jdbcTemplate.queryForObject(sql, String.class, transferTypeId));

        sql = "INSERT INTO transfer_status(transfer_status_desc) " +
                "VALUES('Approved') RETURNING transfer_status_id; ";

        int transferStatusId =jdbcTemplate.queryForObject(sql, Integer.class);

        sql = "SELECT transfer_status_desc FROM transfer_status WHERE transfer_status_id = ?;";

        transfer.setTransferStatusMessage(jdbcTemplate.queryForObject(sql, String.class, transferStatusId));

        sql = "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES(?, ?, ?, ?, ?) RETURNING transfer_id;";

        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, transferTypeId, transferStatusId,
                transfer.getAccountFromId(), transfer.getAccountToId(), transfer.getAmount());

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
        transfer.setTransferTypeMessage(rowSet.getString("transfer_type_desc"));
        transfer.setTransferStatusMessage(rowSet.getString("transfer_status_desc"));
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
