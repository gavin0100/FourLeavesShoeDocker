package com.data.filtro.exception.api.user;

public class ParamNotMatchException extends RuntimeException{
    public ParamNotMatchException(String oldPassword, String newPassword) {
        super("The old password not match with the new password: " + oldPassword + " -> " + newPassword);
    }
}
