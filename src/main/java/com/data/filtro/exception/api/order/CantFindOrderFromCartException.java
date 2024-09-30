package com.data.filtro.exception.api.order;

public class CantFindOrderFromCartException extends RuntimeException{
    public CantFindOrderFromCartException(Long userId){
        super("Can't find order from cart with user id: " + userId);
    }
}
