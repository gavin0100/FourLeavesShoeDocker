package com.data.filtro.exception.api.category;

public class CantGetProductListPageException extends RuntimeException{
    public CantGetProductListPageException() {
        super("Can't get list of products at shop page!");
    }
}
