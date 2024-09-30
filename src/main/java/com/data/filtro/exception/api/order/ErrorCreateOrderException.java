package com.data.filtro.exception.api.order;

public class ErrorCreateOrderException extends RuntimeException{
    public ErrorCreateOrderException(Long userId){
        super("Can't create order from cart with user id: " + userId);
    }
}
