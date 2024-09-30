package com.data.filtro.exception.api.user;

public class CantReloginAfterCheckoutException extends RuntimeException{
    public CantReloginAfterCheckoutException(String userName) {
        super("Can't relogin with username: " + userName);
    }
}
