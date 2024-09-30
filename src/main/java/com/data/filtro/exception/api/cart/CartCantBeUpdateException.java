package com.data.filtro.exception.api.cart;

public class CartCantBeUpdateException extends RuntimeException{
    public CartCantBeUpdateException(Long userId) {
        super("Cart with user id: "+ userId+" can't be updated");
    }
}
