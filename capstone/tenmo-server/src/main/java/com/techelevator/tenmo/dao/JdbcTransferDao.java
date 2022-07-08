package com.techelevator.tenmo.dao;

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
    public Transfer getTransferDetailsById(int transferId) {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
            "FROM transfer WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public String getTransferTypeDescById(int id) {

        String sql = "SELECT transfer_type_desc FROM transfer_type WHERE transfer_type_id = ?; ";

        String transferTypeDesc = jdbcTemplate.queryForObject(sql, String.class, id);

        return transferTypeDesc;
    }

    @Override
    public String getTransferStatusDescById(int id) {

        String sql = "SELECT transfer_status_desc FROM transfer_status WHERE transfer_status_id = ?; ";

        String transferStatusDesc = jdbcTemplate.queryForObject(sql, String.class, id);

        return transferStatusDesc;
    }

    @Override
    public List<Transfer> listAllTransfers(int userId, int transferStatusId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT t.transfer_id, t.transfer_type_id, t.transfer_status_id, t.account_from, t.account_to, t.amount " +
                "FROM transfer t " +
                "JOIN account a " +
                "ON a.account_id = t.account_from " +
                "WHERE a.user_id = ? AND t.transfer_status_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, transferStatusId);
        while(results.next()) {
            transfers.add(mapRowToTransfer(results));
        }

        return transfers;
    }

    @Override
    public List<Transfer> listAllTransfers(int userId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT t.transfer_id, t.transfer_type_id, t.transfer_status_id, t.account_from, t.account_to, t.amount " +
                "FROM transfer t " +
                "JOIN account a " +
                "ON a.account_id = t.account_from " +
                "WHERE a.user_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
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

        return getTransferDetailsById(transferId);
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
        transfer.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        transfer.setAccountFromId(rowSet.getInt("account_from"));
        transfer.setAccountToId(rowSet.getInt("account_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));

        return transfer;
    }

}
