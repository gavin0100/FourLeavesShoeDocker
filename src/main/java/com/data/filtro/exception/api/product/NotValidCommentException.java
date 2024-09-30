package com.data.filtro.exception.api.product;

public class NotValidCommentException extends RuntimeException{
    public NotValidCommentException(String comment) {
        super("This comment is not valid: " + comment);
    }
}
