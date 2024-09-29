package com.data.filtro.exception.api.cart;

public class CartNotFoundException extends RuntimeException{
    public CartNotFoundException(Long userId) {
        super("Cart with user id: "+ userId+" not found");
    }
}
