package com.oleksandr.application.data.repository;

import com.oleksandr.application.data.entity.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository  extends CrudRepository<Payment, Long> {
}
