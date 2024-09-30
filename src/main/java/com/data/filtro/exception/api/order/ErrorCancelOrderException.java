package com.data.filtro.exception.api.order;

public class ErrorCancelOrderException extends RuntimeException{
    public ErrorCancelOrderException(Long orderId){
        super("Failed to cancel the order with order id: " + orderId);
    }
}
