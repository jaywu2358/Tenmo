package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private String transferTypeMessage;
    private String transferStatusMessage;
    private int accountFromId;
    private int accountToId;
//    private String accountFromUsername;
//    private String accountToUsername;
    private BigDecimal amount;

    public Transfer(int transferId, String transferTypeMessage, String transferStatusMessage, int accountFromId, int accountToId, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeMessage = transferTypeMessage;
        this.transferStatusMessage = transferTypeMessage;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
//        this.accountFromUsername = accountFromUsername;
//        this.accountToUsername = accountToUsername;
        this.amount = amount;
    }

    public Transfer() {}

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getTransferTypeMessage() {
        return transferTypeMessage;
    }

    public void setTransferTypeMessage(String transferTypeMessage) {
        this.transferTypeMessage = transferTypeMessage;
    }

    public String getTransferStatusMessage() {
        return transferStatusMessage;
    }

    public void setTransferStatusMessage(String transferStatusMessage) {
        this.transferStatusMessage = transferStatusMessage;
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

//    public String getAccountFromUsername() {
//        return accountFromUsername;
//    }
//
//    public void setAccountFromUsername(String accountFromUsername) {
//        this.accountFromUsername = accountFromUsername;
//    }
//
//    public String getAccountToUsername() {
//        return accountToUsername;
//    }
//
//    public void setAccountToUsername(String accountToUsername) {
//        this.accountToUsername = accountToUsername;
//    }

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
                ", transferTypeMessage='" + transferTypeMessage + '\'' +
                ", transferStatusMessage='" + transferStatusMessage + '\'' +
                ", accountFromId=" + accountFromId +
                ", accountToId=" + accountToId +
                ", amount=" + amount +
                '}';
    }
}
