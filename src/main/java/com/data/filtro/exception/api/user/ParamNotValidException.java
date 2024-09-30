package com.data.filtro.exception.api.user;

public class ParamNotValidException extends RuntimeException{
    public ParamNotValidException() {
        super("Input is not valid");
    }
}
