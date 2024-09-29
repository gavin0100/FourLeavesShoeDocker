package com.data.filtro.exception.api;

public class CartGetException extends RuntimeException{
    public CartGetException(Long cartId) {
        super("Cart with user id: "+ cartId+" can't be get");
    }
}
