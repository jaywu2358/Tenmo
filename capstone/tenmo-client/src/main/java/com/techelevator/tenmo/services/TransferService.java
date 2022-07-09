package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TransferService {

    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public TransferService(String url, String authToken) {
        this.baseUrl = url;
        this.authToken = authToken;
    }

    public Transfer[] listAllTransfers(int userId) {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "transfers?userId=" + userId, HttpMethod.GET,
                    makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public Transfer[] listPendingTransfers(int userId) {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "transfers?userId=" + userId + "&transferStatus=1", HttpMethod.GET,
                    makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public boolean sendTransfer(Account senderAccount, Account recipientAccount, BigDecimal amountToSend) {
        boolean success = false;

        Transfer transferToSend = new Transfer(0, 2, "test", 2, "test", senderAccount.getAccountId(), "test", recipientAccount.getAccountId(), "test", amountToSend);

        Transfer returnedTransfer = null;

        try {
            returnedTransfer = restTemplate.postForObject(baseUrl + "transfers", makeTransferEntity(transferToSend), Transfer.class);
            if (returnedTransfer != null) {
                success = true;
            }
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return success;

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
