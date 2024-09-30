package com.data.filtro.exception.api.product;

public class CantCommentException extends RuntimeException{
    public CantCommentException(Long productId) {
        super("Can't comment with product have id: " + productId);
    }
}
