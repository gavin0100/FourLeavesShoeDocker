package com.data.filtro.exception.api.cart;

public class CartGetException extends RuntimeException{
    public CartGetException(Long userId) {
        super("Cart with user id: "+ userId+" can't be get");
    }
}
