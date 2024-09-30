package com.data.filtro.exception.api.order;

public class MissInformationUserException extends RuntimeException{
    public MissInformationUserException(Long userId){
        super("Miss user's information with user id: " + userId);
    }
}
