package com.data.filtro.exception.api.user;

public class CantResetPasswordException extends RuntimeException{
    public CantResetPasswordException(long userId) {
        super("Can't reset password with user id: " + userId);
    }
}
