package com.data.filtro.exception.api.cart;

public class CartCantAddProductException extends RuntimeException{
    public CartCantAddProductException(Long userId, Long productId) {
        super("Cart with user id: "+ userId+" can't add product id: " + productId);
    }
}
