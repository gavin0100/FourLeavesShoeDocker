package com.data.filtro.exception.api.user;

public class PasswordNotValidException extends RuntimeException{
    public PasswordNotValidException(String password) {
        super("Password is not valid: " + password);
    }
}
