package com.data.filtro.exception.api;

public class CartNotFoundException extends RuntimeException{
    public CartNotFoundException(Long cartId) {
        super("Cart with user id: "+ cartId+" not found");
    }
}
