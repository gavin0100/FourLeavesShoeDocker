package com.data.filtro.exception.api.contact;

public class CantSendContactRequestException extends RuntimeException{
    public CantSendContactRequestException(){
        super("Can't send contact request exception!");
    }
}
