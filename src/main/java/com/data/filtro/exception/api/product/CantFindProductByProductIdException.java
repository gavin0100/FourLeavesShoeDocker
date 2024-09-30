package com.data.filtro.exception.api.product;

public class CantFindProductByProductIdException extends RuntimeException{
    public CantFindProductByProductIdException(Long productId) {
        super("Can't find product by id: " + productId);
    }
}
