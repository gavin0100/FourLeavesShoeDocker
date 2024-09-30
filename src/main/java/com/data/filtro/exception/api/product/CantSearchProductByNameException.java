package com.data.filtro.exception.api.product;

public class CantSearchProductByNameException extends RuntimeException{
    public CantSearchProductByNameException(String name) {
        super("Can't search product by name: " + name);
    }
}
