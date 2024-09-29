package com.data.filtro.exception.api.user;

public class UserNotFoundOrAuthorizeException extends RuntimeException{
    public UserNotFoundOrAuthorizeException() {
        super("User not found or authorize");
    }
}
