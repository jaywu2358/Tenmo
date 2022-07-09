package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.controller.AccountController;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        Transfer transfer = null;
        String sql = "SELECT t.transfer_id, t.transfer_type_id, tt.transfer_type_desc, t.transfer_status_id, " +
                "ts.transfer_status_desc, t.account_from, tu_f.username AS user_from, t.account_to, tu_t.username " +
                "AS user_to, t.amount " +
                "FROM transfer t " +
                "JOIN transfer_type tt " +
                "ON t.transfer_type_id = tt.transfer_type_id " +
                "JOIN transfer_status ts " +
                "ON t.transfer_status_id = ts.transfer_status_id " +
                "JOIN account acc_f " +
                "ON t.account_from = acc_f.account_id " +
                "JOIN account acc_t " +
                "ON t.account_to = acc_t.account_id " +
                "JOIN tenmo_user tu_f " +
                "ON acc_f.user_id = tu_f.user_id " +
                "JOIN tenmo_user tu_t " +
                "ON acc_t.user_id = tu_t.user_id " +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public List<Transfer> filterTransfersByStatus(int userId, int transferStatusId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT t.transfer_id, t.transfer_type_id, tt.transfer_type_desc, t.transfer_status_id, " +
                "ts.transfer_status_desc, t.account_from, tu_f.username AS user_from, t.account_to, tu_t.username " +
                "AS user_to, t.amount " +
                "FROM transfer t " +
                "JOIN transfer_type tt " +
                "ON t.transfer_type_id = tt.transfer_type_id " +
                "JOIN transfer_status ts " +
                "ON t.transfer_status_id = ts.transfer_status_id " +
                "JOIN account acc_f " +
                "ON t.account_from = acc_f.account_id " +
                "JOIN account acc_t " +
                "ON t.account_to = acc_t.account_id " +
                "JOIN tenmo_user tu_f " +
                "ON acc_f.user_id = tu_f.user_id " +
                "JOIN tenmo_user tu_t " +
                "ON acc_t.user_id = tu_t.user_id " +
                "WHERE (tu_t.user_id = ? OR tu_f.user_id = ?) AND t.transfer_status_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId, transferStatusId);
        while(results.next()) {
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }

    @Override
    public List<Transfer> filterTransfersByType(int userId, int transferTypeId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT t.transfer_id, t.transfer_type_id, tt.transfer_type_desc, t.transfer_status_id, " +
                "ts.transfer_status_desc, t.account_from, tu_f.username AS user_from, t.account_to, tu_t.username " +
                "AS user_to, t.amount " +
                "FROM transfer t " +
                "JOIN transfer_type tt " +
                "ON t.transfer_type_id = tt.transfer_type_id " +
                "JOIN transfer_status ts " +
                "ON t.transfer_status_id = ts.transfer_status_id " +
                "JOIN account acc_f " +
                "ON t.account_from = acc_f.account_id " +
                "JOIN account acc_t " +
                "ON t.account_to = acc_t.account_id " +
                "JOIN tenmo_user tu_f " +
                "ON acc_f.user_id = tu_f.user_id " +
                "JOIN tenmo_user tu_t " +
                "ON acc_t.user_id = tu_t.user_id " +
                "WHERE (tu_t.user_id = ? OR tu_f.user_id = ?) AND t.transfer_type_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId, transferTypeId);
        while(results.next()) {
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }

    @Override
    public List<Transfer> filterTransfersByTypeAndStatus(int userId, int transferTypeId, int transferStatusId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT t.transfer_id, t.transfer_type_id, tt.transfer_type_desc, t.transfer_status_id, " +
                "ts.transfer_status_desc, t.account_from, tu_f.username AS user_from, t.account_to, tu_t.username " +
                "AS user_to, t.amount " +
                "FROM transfer t " +
                "JOIN transfer_type tt " +
                "ON t.transfer_type_id = tt.transfer_type_id " +
                "JOIN transfer_status ts " +
                "ON t.transfer_status_id = ts.transfer_status_id " +
                "JOIN account acc_f " +
                "ON t.account_from = acc_f.account_id " +
                "JOIN account acc_t " +
                "ON t.account_to = acc_t.account_id " +
                "JOIN tenmo_user tu_f " +
                "ON acc_f.user_id = tu_f.user_id " +
                "JOIN tenmo_user tu_t " +
                "ON acc_t.user_id = tu_t.user_id " +
                "WHERE (tu_t.user_id = ? OR tu_f.user_id = ?) AND t.transfer_type_id = ? AND t.transfer_status_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId, transferTypeId, transferStatusId);
        while(results.next()) {
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }

    @Override
    public List<Transfer> listAllTransfers(int userId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT t.transfer_id, t.transfer_type_id, tt.transfer_type_desc, t.transfer_status_id, " +
        "ts.transfer_status_desc, t.account_from, tu_f.username AS user_from, t.account_to, tu_t.username " +
                "AS user_to, t.amount " +
                "FROM transfer t " +
                "JOIN transfer_type tt " +
                "ON t.transfer_type_id = tt.transfer_type_id " +
                "JOIN transfer_status ts " +
                "ON t.transfer_status_id = ts.transfer_status_id " +
                "JOIN account acc_f " +
                "ON t.account_from = acc_f.account_id " +
                "JOIN account acc_t " +
                "ON t.account_to = acc_t.account_id " +
                "JOIN tenmo_user tu_f " +
                "ON acc_f.user_id = tu_f.user_id " +
                "JOIN tenmo_user tu_t " +
                "ON acc_t.user_id = tu_t.user_id " +
                "WHERE tu_t.user_id = ? OR tu_f.user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while(results.next()) {
            transfers.add(mapRowToTransfer(results));
        }

        return transfers;
    }

    @Override
    public Transfer createTransfer(Transfer transfer) {

        String sql = "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES(?, ?, ?, ?, ?) RETURNING transfer_id;";

        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransferTypeId(), transfer.getTransferStatusId(),
                transfer.getAccountFromId(), transfer.getAccountToId(), transfer.getAmount());

        return getTransferById(transferId);
    }

    @Override
    public void updateTransferStatus(Transfer transfer) {
        String sql = "UPDATE transfers " +
                    "SET transfer_status_id ? " +
                    "WHERE transfer_id = ?; ";
        jdbcTemplate.update(sql,transfer.getTransferStatusId(),transfer.getTransferId());
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();

        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setTransferTypeId(rowSet.getInt("transfer_type_id"));
        transfer.setTransferTypeDesc(rowSet.getString("transfer_type_desc"));
        transfer.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        transfer.setTransferStatusDesc(rowSet.getString("transfer_status_desc"));
        transfer.setAccountFromId(rowSet.getInt("account_from"));
        transfer.setAccountFromUsername(rowSet.getString("user_from"));
        transfer.setAccountToId(rowSet.getInt("account_to"));
        transfer.setAccountToUsername(rowSet.getString("user_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));

        return transfer;
    }

}
