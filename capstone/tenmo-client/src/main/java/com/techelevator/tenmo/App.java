package com.techelevator.tenmo;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
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
    private int userId;

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
            this.userId = accountService.getUserAccount().getUserId();
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

	private void viewCurrentBalance() {
		consoleService.printAccountBalance(accountService.getUserAccount());
	}

    // Jay
	private void viewTransferHistory() {
		// TODO Auto-generated method stub
        consoleService.printTransferHistory(1001, "Jonathan/Jay", BigDecimal.valueOf(200));
		
	}

    // Jay
	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

    // Jonathan - Partially done
	private void sendBucks() {
        consoleService.printUsers(accountService.listUsers());
        int recipientId = consoleService.promptForInt("Enter recipient's user ID (0 to cancel): ");
        boolean isRecipientIdValid = false;
        if (recipientId == userId) {
            System.out.println("You can't send money to yourself.");
        } else if (recipientId != 0) {
            isRecipientIdValid = accountService.validateId(recipientId);
        }

        if (isRecipientIdValid) {
            BigDecimal amountToSend = consoleService.promptForBigDecimal("Enter amount: ");

            // Change these to users
            Account userAccount = accountService.getUserAccount();
            Account recipientAccount = accountService.getAccountByUserId(recipientId);
            boolean success = transferService.sendTransfer(userAccount, recipientAccount, amountToSend);
            System.out.println(success);
        }

    }

    // Optional Use Case -- Revisit when initial app is built
	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
