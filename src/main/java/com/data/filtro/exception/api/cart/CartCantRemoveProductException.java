package com.data.filtro.exception.api.cart;

public class CartCantRemoveProductException extends RuntimeException{
    public CartCantRemoveProductException(Long userId, Long productId) {
        super("Cart with user id: "+ userId+" can't remove product id: " + productId);
    }
}
