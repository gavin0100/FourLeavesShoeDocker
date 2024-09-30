package com.data.filtro.exception.api.invoice;

public class CantExportFilePDFInvoiceException extends RuntimeException{
    public CantExportFilePDFInvoiceException(Long orderId){
        super("Can't export file pdf invoice with order id: " + orderId);
    }
}
