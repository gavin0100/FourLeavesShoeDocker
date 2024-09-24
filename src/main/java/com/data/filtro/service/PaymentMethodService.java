package com.data.filtro.service;

import com.data.filtro.model.PaymentMethod;
import com.data.filtro.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    public  PaymentMethod getMethodById(int id){return paymentMethodRepository.getMethodById(id);}
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }
}
