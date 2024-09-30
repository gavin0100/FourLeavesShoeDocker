package com.data.filtro.exception.api.cart;

public class ProductsNotMatchQuantitiesException extends RuntimeException{
    public ProductsNotMatchQuantitiesException() {
        super("The length product's array not match with quantity's array");
    }
}
