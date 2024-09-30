package com.data.filtro.exception.api.user;

public class CantGetBillingException extends RuntimeException{
    public CantGetBillingException(long userId) {
        super("Can't get the list of bill of user id: " + userId);
    }
}
