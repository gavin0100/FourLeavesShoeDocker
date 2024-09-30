package com.data.filtro.exception.controller;

public class AccountNameExistException extends RuntimeException {
    public AccountNameExistException(String message) {
        super(message);
    }
}
