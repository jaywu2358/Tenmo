package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

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
                System.out.println("Invalid selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		consoleService.printAccountBalance(accountService.getUserAccount());
	}

	private void viewTransferHistory() {
        int menuSelection = -1;
        Integer transferId;

        while(menuSelection != 0) {

            int currentUserId = currentUser.getUser().getId().intValue();

            Transfer[] transfers = transferService.listAllTransfers(currentUserId);

            if (transfers.length == 0) {
                System.out.println("You have no previous transfers.");
                break;
            }

            consoleService.printTransferHeading("Transfer History");

            for (Transfer transfer : transfers) {
                transferId = transfer.getTransferId();
                if (transfer.getAccountToUsername().equals(currentUser.getUser().getUsername())) {
                    consoleService.printTransferHistory(transferId, "From: " + transfer.getAccountFromUsername(), transfer.getAmount());
                } else if (transfer.getAccountFromUsername().equals(currentUser.getUser().getUsername())) {
                    consoleService.printTransferHistory(transferId, "To: " + transfer.getAccountToUsername(), transfer.getAmount());
                }
            }

            System.out.println();
            menuSelection = consoleService.promptForMenuSelection("Please enter transfer ID to view details (0 to cancel): ");
            boolean validTransferId = false;

            for (Transfer transfer : transfers) {
                transferId = transfer.getTransferId();
                if (transferId == menuSelection) {
                    validTransferId = true;
                    consoleService.printTransferDetails(transfer);
                }
            }
            if (!validTransferId && menuSelection != 0) {
                consoleService.printInvalidSelectionError("transfer");
            } else if (validTransferId) {
                consoleService.pause();
            }
        }
    }

	private void viewPendingRequests() {
        int menuSelection = -1;

        while (menuSelection != 0) {

            int currentUserId = currentUser.getUser().getId().intValue();
            Integer transferId = null;

            Transfer[] transfers = transferService.listAllPendingTransfers(currentUserId, 1);

            if (transfers.length == 0) {
                System.out.println("There are no pending requests.");
                break;
            }

            consoleService.printTransferHeading("Pending Transfers");

            for (Transfer transfer : transfers) {
                transferId = transfer.getTransferId();
                if (transfer.getAccountToUsername().equals(currentUser.getUser().getUsername())) {
                    consoleService.printTransferHistory(transferId, "From: " + transfer.getAccountFromUsername(), transfer.getAmount());
                } else if (transfer.getAccountFromUsername().equals(currentUser.getUser().getUsername())) {
                    consoleService.printTransferHistory(transferId, "To: " + transfer.getAccountToUsername(), transfer.getAmount());
                }
            }

            System.out.println();
            menuSelection = consoleService.promptForMenuSelection("Please enter transfer ID to view details OR to approve/reject transfer (0 to cancel): ");
            boolean validTransferId = false;

            for (Transfer transfer : transfers) {
                transferId = transfer.getTransferId();
                if (transferId.equals(menuSelection)) {
                    validTransferId = true;
                    consoleService.printTransferDetails(transfer);

                    if (transfer.getAccountToUsername().equals(currentUser.getUser().getUsername())) {
                        consoleService.printTransferApprovalOptions();
                        int selection = consoleService.promptForInt("Please choose an option: ");
                        if (selection == 1 || selection == 2) {
                            if (selection == 1) {
                                if (accountService.getUserAccount().getBalance().compareTo(transfer.getAmount()) > 0) {
                                    transfer.setTransferStatusId(2);
                                    Transfer returnedTransfer = transferService.approveOrRejectTransfer(transfer);
                                    consoleService.printTransferDetails(returnedTransfer);
                                } else {
                                    consoleService.printInsufficientFundsMessage();
                                    consoleService.pause();
                                }
                            } else {
                                transfer.setTransferStatusId(3);
                                Transfer returnedTransfer = transferService.approveOrRejectTransfer(transfer);
                                System.out.println("You have rejected this request.\r\n");
                                consoleService.pause();
                                consoleService.printTransferDetails(returnedTransfer);
                            }
                        } else if (selection != 0) {
                            System.out.println("Invalid selection");
                        }
                    } else {
                        System.out.println("You don't have the ability to approve/reject this transfer.");
                    }
                }
            }
            if (!validTransferId && menuSelection != 0) {
                consoleService.printInvalidSelectionError("transfer");
            }
        }
    }

	private void sendBucks() {
        int menuSelection = -1;

        while (menuSelection != 0) {
            int currentUserId = currentUser.getUser().getId().intValue();
            User[] users = accountService.listUsers();
            if (users.length == 1) {
                System.out.println("There are no other users registered.");
                break;
            }

            List<Long> userIds = new ArrayList<>();
            for (User user : users) {
                userIds.add(user.getId());
            }
            consoleService.printUsers(users, currentUserId);
            menuSelection = consoleService.promptForUserId("Enter recipient's user ID (0 to cancel): ");
            int recipientId = menuSelection;

            boolean isRecipientIdValid = false;

            if (recipientId == currentUserId) {
                System.out.println("You can't send money to yourself.");
            } else if (recipientId == 0) {
                break;
            } else if (userIds.contains((long) recipientId)) {
                isRecipientIdValid = true;
            } else {
                consoleService.printInvalidSelectionError("user");
            }

            if (isRecipientIdValid) {
                boolean shouldContinue = true;
                while (shouldContinue) {
                    BigDecimal amountToSend = consoleService.promptForBigDecimal("Enter amount: ");
                    if (amountToSend.compareTo(BigDecimal.ZERO) <= 0) {
                        System.out.println("You must enter a positive or non-zero value.");
                    } else if (amountToSend.compareTo(BigDecimal.valueOf(9999.99)) > 0) {
                        System.out.println("You can only send less than $10,000.00 using TEnmo.");
                    } else if (accountService.getUserAccount().getBalance().compareTo(amountToSend) < 0) {
                        consoleService.printInsufficientFundsMessage();
                    } else {
                        Transfer transfer = transferService.sendTransfer(currentUserId, recipientId, amountToSend);
                        if (transfer != null) {
                            consoleService.printTransferDetails(transfer);
                        } else {
                            consoleService.printErrorMessage();
                        }
                        shouldContinue = false;
                    }
                }
                break;
            }
        }
    }

	private void requestBucks() {
        int menuSelection = -1;

        while (menuSelection != 0) {
            int currentUserId = currentUser.getUser().getId().intValue();
            User[] users = accountService.listUsers();
            if (users.length == 1) {
                System.out.println("There are no other users registered.");
                break;
            }

            List<Long> userIds = new ArrayList<>();
            for (User user : users) {
                userIds.add(user.getId());
            }
            consoleService.printUsers(users, currentUserId);
            menuSelection = consoleService.promptForUserId("Enter the user ID you'd like to request money from " +
                    "(0 to cancel): ");
            int recipientId = menuSelection;

            boolean isRecipientIdValid = false;

            if (recipientId == currentUserId) {
                System.out.println("You can't request money from yourself.");
            } else if (recipientId == 0) {
                break;
            } else if (userIds.contains((long) recipientId)) {
                isRecipientIdValid = true;
            } else {
                consoleService.printInvalidSelectionError("user");
            }

            if (isRecipientIdValid) {
               boolean shouldContinue = true;
               while (shouldContinue) {
                   BigDecimal amountToRequest = consoleService.promptForBigDecimal("Enter amount: ");
                   if (amountToRequest.compareTo(BigDecimal.ZERO) <= 0) {
                       System.out.println("You must enter a positive or non-zero value.");
                   } else if (amountToRequest.compareTo(BigDecimal.valueOf(9999.99)) > 0) {
                       System.out.println("You can only request less than $10,000.00 using TEnmo.");
                   } else {
                       Transfer transfer = transferService.requestTransfer(currentUserId, recipientId, amountToRequest);
                       if (transfer != null) {
                           consoleService.printTransferDetails(transfer);
                       } else {
                           consoleService.printErrorMessage();
                       }
                       shouldContinue = false;
                   }
               }
               break;
            }
        }
		
	}

}
