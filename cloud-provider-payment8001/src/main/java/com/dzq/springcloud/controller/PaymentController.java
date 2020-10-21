package com.dzq.springcloud.controller;

import com.dzq.springcloud.entities.CommonResult;
import com.dzq.springcloud.entities.Payment;
import com.dzq.springcloud.service.IPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        String code, message;
        if (result > 0) {
            code = "200";
            message = "新增成功,端口号：" + serverPort;
        } else {
            code = "400";
            message = "新增失败";
        }
        return new CommonResult(code, message);
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);

        String code, message;
        if (null != payment) {
            code = "200";
            message = "查询成功,端口号：" + serverPort;
        } else {
            code = "400";
            message = "查询失败";
        }
        return new CommonResult(code, message, payment);
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("----------element:" + service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info("serviceId:[{}], 主机名称:[{}], 端口号:[{}], url地址:[{}]", instance.getServiceId(), instance.getHost(), instance.getPort(), instance.getUri());
        }
        return discoveryClient;
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String timeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        return serverPort;
    }
}
