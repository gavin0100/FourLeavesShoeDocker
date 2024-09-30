package com.data.filtro.exception.api.invoice;

public class CantFindInvoiceWithOrderIdException extends RuntimeException{
    public CantFindInvoiceWithOrderIdException(Long orderId){
        super("Can't find invoice with order id: " + orderId);
    }
}
