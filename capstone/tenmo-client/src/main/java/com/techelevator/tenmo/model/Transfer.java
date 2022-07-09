package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private int transferTypeId;
    private String transferTypeDesc;
    private int transferStatusId;
    private String transferStatusDesc;
    private int accountFromId;
    private String accountFromUsername;
    private int accountToId;
    private String accountToUsername;

    private BigDecimal amount;

    public Transfer() {}

    public Transfer(int transferId, int transferTypeId, String transferTypeDesc, int transferStatusId,
                    String transferStatusDesc, int accountFromId, String accountFromUsername, int accountToId,
                    String accountToUsername, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferTypeDesc = transferTypeDesc;
        this.transferStatusId = transferStatusId;
        this.transferStatusDesc = transferStatusDesc;
        this.accountFromId = accountFromId;
        this.accountFromUsername = accountFromUsername;
        this.accountToId = accountToId;
        this.accountToUsername = accountToUsername;
        this.amount = amount;
    }

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

    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }

    public int getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(int accountFromId) {
        this.accountFromId = accountFromId;
    }

    public String getAccountFromUsername() {
        return accountFromUsername;
    }

    public void setAccountFromUsername(String accountFromUsername) {
        this.accountFromUsername = accountFromUsername;
    }

    public int getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(int accountToId) {
        this.accountToId = accountToId;
    }

    public String getAccountToUsername() {
        return accountToUsername;
    }

    public void setAccountToUsername(String accountToUsername) {
        this.accountToUsername = accountToUsername;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transfer Id: " + transferId +
        "\r\nFrom: " + accountFromUsername +
        "\r\nTo: " + accountToUsername +
        "\r\nType: " + transferTypeDesc +
        "\r\nStatus: " + transferStatusDesc +
        "\r\nAmount: $" + amount;
    }
}
