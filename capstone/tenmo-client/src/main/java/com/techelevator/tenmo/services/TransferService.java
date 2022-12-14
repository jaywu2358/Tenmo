package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TransferService {

    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;
    private final int TYPE_REQUEST = 1;
    private final int TYPE_SEND = 2;
    private final int STATUS_PENDING = 1;
    private final int STATUS_APPROVED = 2;
    private final int STATUS_REJECTED = 3;

    public TransferService(String url, String authToken) {
        this.baseUrl = url;
        this.authToken = authToken;
    }

    public Transfer[] listAllTransfers(int userId) {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "transfers?userId=" + userId + "&transferStatusId=" + STATUS_APPROVED,
                    HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public Transfer[] listAllPendingTransfers(int userId, int transferStatusId) {
        Transfer[] transfers = null;
        try {
                ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "transfers?userId=" + userId +
                                "&transferStatusId=" + transferStatusId , HttpMethod.GET, makeAuthEntity(), Transfer[].class);
                transfers = response.getBody();

            } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public Transfer getTransferByTransferId(int transferId) {
        Transfer transfer = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "transfers/" + transferId , HttpMethod.GET,
                    makeAuthEntity(), Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }


    public Transfer sendTransfer(int userId, int recipientId, BigDecimal amountToSend) {
        Transfer transferToSend = new Transfer(0, TYPE_SEND, "", STATUS_APPROVED,
                "", userId, "", recipientId, "", amountToSend);
        Transfer returnedTransfer = null;

        try {
            returnedTransfer = restTemplate.postForObject(baseUrl + "transfers", makeTransferEntity(transferToSend), Transfer.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return returnedTransfer;
    }

    public Transfer requestTransfer(int userId, int recipientId, BigDecimal amountToSend) {
        Transfer transferToSend = new Transfer(0, TYPE_REQUEST, "", STATUS_PENDING,
                "", userId, "", recipientId, "", amountToSend);
        Transfer returnedTransfer = null;

        try {
            returnedTransfer = restTemplate.postForObject(baseUrl + "transfers/request", makeTransferEntity(transferToSend), Transfer.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return returnedTransfer;
    }

    public Transfer approveOrRejectTransfer(Transfer transfer) {
        Transfer updatedTransfer = null;
        try {
            restTemplate.put(baseUrl + "transfers", makeTransferEntity(transfer), Transfer.class);
            updatedTransfer = getTransferByTransferId(transfer.getTransferId());
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return updatedTransfer;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}