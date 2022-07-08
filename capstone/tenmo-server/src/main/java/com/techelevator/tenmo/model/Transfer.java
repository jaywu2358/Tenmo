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

//    public Transfer(int transferId, String transferTypeMessage, String transferStatusMessage, int accountFromId, int accountToId, BigDecimal amount) {
//        this.transferId = transferId;
//        this.transferTypeMessage = transferTypeMessage;
//        this.transferStatusMessage = transferTypeMessage;
//        this.accountFromId = accountFromId;
//        this.accountToId = accountToId;
//        this.accountFromUsername = accountFromUsername;
//        this.accountToUsername = accountToUsername;
//        this.amount = amount;
//    }

    public Transfer(int transferId, int transferTypeId, String transferTypeMessage, int transferStatusId, String transferStatusMessage, int accountFromId, int accountToId, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.transferTypeMessage = transferTypeMessage;
        this.transferStatusMessage = transferStatusMessage;
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

    public void setAccountFromId(int accountFromId) {
        this.accountFromId = accountFromId;
    }

    public int getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(int accountToId) {
        this.accountToId = accountToId;
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
                ", transferStatusMessage='" + transferStatusMessage + '\'' +
                ", transferTypeMessage='" + transferTypeMessage + '\'' +
                ", amount=" + amount +
                '}';
    }
}
