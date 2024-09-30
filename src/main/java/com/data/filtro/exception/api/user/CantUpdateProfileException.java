package com.data.filtro.exception.api.user;

public class CantUpdateProfileException extends RuntimeException{
    public CantUpdateProfileException(long userId) {
        super("Can't update profile of user id: " + userId);
    }
}
