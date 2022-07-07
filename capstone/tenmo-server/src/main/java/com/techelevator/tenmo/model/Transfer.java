package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private String transferTypeMessage;
    private String transferStatusMessage;
    private String fromUsername;
    private int fromAccountId;
    private String toUsername;
    private int toAccountId;
    private BigDecimal amount;

    public Transfer(int transferId, String transferTypeMessage, String transferStatusMessage, String fromUsername, int fromAccountId, String toUsername, int toAccountId, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeMessage = transferTypeMessage;
        this.transferStatusMessage = transferStatusMessage;
        this.fromUsername = fromUsername;
        this.fromAccountId = fromAccountId;
        this.toUsername = toUsername;
        this.toAccountId = toAccountId;
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

    public String getFromUsername() {
        return fromUsername;
    }

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
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

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
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
                ", transferTypeMessage='" + transferTypeMessage + '\'' +
                ", transferStatusMessage='" + transferStatusMessage + '\'' +
                ", fromUsername='" + fromUsername + '\'' +
                ", toUsername='" + toUsername + '\'' +
                ", amount=" + amount +
                '}';
    }
}
