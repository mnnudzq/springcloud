package com.dzq.springcloud.controller;

import com.dzq.springcloud.entities.CommonResult;
import com.dzq.springcloud.entities.Payment;
import com.dzq.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class FeignOrderController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/create")
    public CommonResult create(Payment payment) {
        return paymentFeignService.create(payment);
    }

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id) {
        return paymentFeignService.getPaymentById(id);
    }
}
