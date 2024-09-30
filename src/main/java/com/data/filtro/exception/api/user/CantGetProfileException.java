package com.data.filtro.exception.api.user;

public class CantGetProfileException extends RuntimeException{
    public CantGetProfileException(long userId) {
        super("Can't get profile of user id: " + userId);
    }
}
