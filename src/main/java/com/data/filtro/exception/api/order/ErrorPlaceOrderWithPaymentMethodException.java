package com.data.filtro.exception.api.order;

public class ErrorPlaceOrderWithPaymentMethodException extends RuntimeException{
    public ErrorPlaceOrderWithPaymentMethodException(Long userId, String paymentMethod){
        super("Can't place order from cart with user id: " + userId + ": " + paymentMethod);
    }
}
