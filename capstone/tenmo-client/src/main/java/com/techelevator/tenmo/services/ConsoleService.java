package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import jdk.swing.interop.SwingInterOpUtils;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printUsers(User[] accounts, int currentUserId) {
        System.out.println("--------------------------------------------");
        System.out.println("Users");
        System.out.println("ID         NAME");
        System.out.println("--------------------------------------------");
        String idFormat = "%-10s";
        String nameFormat = "%-26s";
        String formatInfo = idFormat + " " + nameFormat + "\r\n";

        for (User user : accounts) {
            if (user.getId() != currentUserId) {
                System.out.format(formatInfo, user.getId(), user.getUsername());
            }
        }

        System.out.println("---------\r\n");
    }

    public void printTransferHistory(int id, String fromAndToAccount, BigDecimal amount) {
        String idFormat = "%-10s";    // min 10 characters, left aligned
        String fromToFormat = "%-23s";  // min 25 characters, left aligned
        String amountFormat = "%9s";   // fixed size 6 characters, right aligned
        String formatInfo = idFormat + " " + fromToFormat + " " + amountFormat;

        System.out.format(formatInfo, id, fromAndToAccount, "$ " + amount);
        System.out.println();
    }

    public void printTransferDetails(Transfer transfer) {
        System.out.println("--------------------------------------------");
        System.out.println("Transfer Details");
        System.out.println("--------------------------------------------");
        System.out.println(transfer.toString());
        System.out.println();
    }

    public void printAccountBalance(Account account) {
        System.out.println("Your current account balance is: $" + account.getBalance());
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void printInvalidSelectionError(String word) {
        System.out.println("--------------------------------------------------------------");
        System.out.println("Invalid " + word + " id. Please enter a " + word + " ID from the list!");
        System.out.println();
    }
}
