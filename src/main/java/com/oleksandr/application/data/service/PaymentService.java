package com.oleksandr.application.data.service;

import com.oleksandr.application.data.entity.Payment;
import com.oleksandr.application.data.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PaymentService{
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void savePayment(Payment payment){
        assert payment!=null;
        paymentRepository.save(payment);
    }
}
