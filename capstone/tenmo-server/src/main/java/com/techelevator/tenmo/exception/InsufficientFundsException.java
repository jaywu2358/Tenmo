package com.techelevator.tenmo.exception;

public class InsufficientFundsException extends Exception {

    public InsufficientFundsException() {
        super("You don't have enough money to complete this transaction.");
    }
}
