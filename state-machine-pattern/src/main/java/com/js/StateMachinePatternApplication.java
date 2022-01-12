package com.js;

import cn.hutool.extra.spring.SpringUtil;
import com.js.spring.service.IOrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StateMachinePatternApplication {

    public static void main(String[] args) {
        SpringApplication.run(StateMachinePatternApplication.class, args);

        IOrderService orderService = SpringUtil.getBean("orderService");
        orderService.create();
        orderService.create();
        orderService.pay(1);
        new Thread("客户线程") {
            @Override
            public void run() {
                orderService.deliver(1);
                orderService.receive(1);
            }
        }.start();
        orderService.pay(2);
        orderService.deliver(2);
        orderService.receive(2);
        System.out.println("全部订单状态：" + orderService.getOrders());
    }

}
