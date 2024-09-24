package com.data.filtro.service;

import com.data.filtro.model.InvoiceDetail;
import com.data.filtro.repository.InvoiceDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceDetailService {

    private final InvoiceDetailRepository invoiceDetailRepository;

    
}
