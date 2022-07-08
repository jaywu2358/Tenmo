package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private int transferTypeId;
    private int transferStatusId;
    private int accountFromId;
    private int accountToId;
//    private String accountFromUsername;
//    private String accountToUsername;
    private String transferStatusMessage;
    private String transferTypeMessage;
    private BigDecimal amount;

//    public Transfer(int transferId, int transferTypeId, String transferTypeMessage, int transferStatusId, String transferStatusMessage, int accountFromId, int accountToId, BigDecimal amount) {
//        this.transferId = transferId;
//        this.transferTypeId = transferTypeId;
//        this.transferStatusId = transferStatusId;
//        this.accountFromId = accountFromId;
//        this.accountToId = accountToId;
//        this.transferTypeMessage = transferTypeMessage;
//        this.transferStatusMessage = transferStatusMessage;
//        this.amount = amount;
//    }

    public Transfer(int transferId, int transferTypeId, int transferStatusId, int accountFromId, int accountToId, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.amount = amount;
    }

    public Transfer() {}

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public String getTransferStatusMessage() {
        return transferStatusMessage;
    }

    public void setTransferStatusMessage(String transferStatusMessage) {
        this.transferStatusMessage = transferStatusMessage;
    }

    public String getTransferTypeMessage() {
        return transferTypeMessage;
    }

    public void setTransferTypeMessage(String transferTypeMessage) {
        this.transferTypeMessage = transferTypeMessage;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getAccountFromId() {
        return accountFromId;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transferId +
                ", transferTypeId=" + transferTypeId +
                ", transferStatusId=" + transferStatusId +
                ", accountFromId=" + accountFromId +
                ", accountToId=" + accountToId +
                ", amount=" + amount +
                '}';
    }
}
