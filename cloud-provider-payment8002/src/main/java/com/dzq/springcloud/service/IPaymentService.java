package com.dzq.springcloud.service;

import com.dzq.springcloud.entities.Payment;

public interface IPaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(Long id);
}
