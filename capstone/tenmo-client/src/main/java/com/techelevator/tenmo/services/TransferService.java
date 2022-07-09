package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public TransferService(String url, String authToken) {
        this.baseUrl = url;
        this.authToken = authToken;
    }

//    public Transfer[] listAllTransfers(int userId, int transferTypeId, int transferStatusId) {
//        Transfer[] transfers = null;
//        try {
//            if (transferStatusId == 0 && transferTypeId == 0) {
//                ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "transfers?userId=" + userId,
//                        HttpMethod.GET, makeAuthEntity(), Transfer[].class);
//                transfers = response.getBody();
//            }
//            else if(transferTypeId > 0 && transferStatusId == 0) {
//                ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "transfers?userId=" + userId
//                                + "&transferTypeId=" + transferTypeId, HttpMethod.GET,
//                        makeAuthEntity(), Transfer[].class);
//                transfers = response.getBody();
//            }
//            else if(transferTypeId == 0 && transferStatusId > 0) {
//                ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "transfers?userId=" + userId
//                                + "&transferStatusId=" + transferStatusId, HttpMethod.GET,
//                        makeAuthEntity(), Transfer[].class);
//            }
//            else {
//                ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "transfers?userId=" + userId
//                                +"&transferTypeId=" + transferTypeId +"&transferStatusId=" + transferStatusId, HttpMethod.GET,
//                        makeAuthEntity(), Transfer[].class);
//            }
//        } catch (RestClientResponseException | ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
//        return transfers;
//    }

    public Transfer[] listAllTransfers(int userId) {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "transfers?userId=" + userId,
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
