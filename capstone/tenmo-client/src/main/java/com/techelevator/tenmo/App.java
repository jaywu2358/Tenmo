package com.techelevator.tenmo;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;
    private AccountService accountService;
    private TransferService transferService;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void initializeServices() {
        accountService = new AccountService(API_BASE_URL, currentUser.getToken());
        transferService = new TransferService(API_BASE_URL, currentUser.getToken());
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            initializeServices();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    // Done
	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
        Account userAccount = accountService.getUserAccount();
		consoleService.printAccountBalance(userAccount);
	}

    // Jay
	private void viewTransferHistory() {
		// TODO Auto-generated method stub
        int menuSelection = -1;
        Integer transferId;

        while(menuSelection!= 0 ) {

            int currentUserId = accountService.getUserAccount().getUserId();

            Transfer[] transfers = transferService.listAllTransfers(currentUserId);

            for (Transfer transfer : transfers) {
                transferId = transfer.getTransferId();
                consoleService.printTransferHistory(transferId, transfer.getAccountFromUsername() + "/"
                        + transfer.getAccountToUsername(), transfer.getAmount());
            }

            //List details
            System.out.println();
            menuSelection = consoleService.promptForMenuSelection("Please enter transfer ID to view details (0 to cancel): ");
            boolean validTransferId = false;

            for (Transfer transfer : transfers) {
                transferId = transfer.getTransferId();
                if (transferId == menuSelection) {
                    validTransferId = true;
                    consoleService.printTransferDetails(menuSelection, transfer.getAccountFromUsername(),
                            transfer.getAccountToUsername(), transfer.getTransferTypeDesc(), transfer.getTransferStatusDesc(),
                            transfer.getAmount());
                    consoleService.pause();
                } else if (menuSelection == 0) {
                    mainMenu();
                    break;
                }
            }
            if(!validTransferId) {
                System.out.println("--------------------------------------------------------------");
                System.out.println("Invalid transfer ID. Please enter a transfer ID from the list!");
                consoleService.pause();
                System.out.println();
            }
        }
    }


    // Jay
	private void viewPendingRequests() {
        // TODO Auto-generated method stub
        int menuSelection = -1;

        while(menuSelection!= 0 ) {

            int currentUserId = accountService.getUserAccount().getUserId();
            Integer transferId = null;

            Transfer[] transfers = transferService.listAllPendingTransfers(currentUserId, 1);

            for (Transfer transfer : transfers) {
                transferId = transfer.getTransferId();
                consoleService.printTransferHistory(transferId, transfer.getAccountFromUsername() + "/"
                        + transfer.getAccountToUsername(), transfer.getAmount());
            }

            System.out.println();
            menuSelection = consoleService.promptForMenuSelection("Please enter transfer ID to view details (0 to cancel): ");
            boolean validTransferId = false;

            //List details
            for (Transfer transfer : transfers) {
                transferId = transfer.getTransferId();
                if (transferId.equals(menuSelection)) {
                    validTransferId = true;
                    consoleService.printTransferDetails(transferId, transfer.getAccountFromUsername(),
                            transfer.getAccountToUsername(), transfer.getTransferTypeDesc(), transfer.getTransferStatusDesc(),
                            transfer.getAmount());
                    consoleService.pause();
                } else if (menuSelection == 0){
                    mainMenu();
                    break;
                }
            }
            if(!validTransferId) {
                System.out.println("--------------------------------------------------------------");
                System.out.println("Invalid transfer ID. Please enter a transfer ID from the list!");
                consoleService.pause();
                System.out.println();
            }
        }
    }

    // Jonathan
	private void sendBucks() {
		// TODO Auto-generated method stub
        consoleService.printUsers(accountService.listUsers());

	}

    // Optional Use Case -- Revisit when initial app is built
	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
