package com.chen.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @ClassName OrderService
 * @Description TODO
 * @Author xiaochen
 * @Date 2021/7/5 9:12
 */
@Service
public class OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 模拟用户下订单
    public void makeOrder(String userid, String productid, int num) {

        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生产成功：" + orderId);
        String exchangeName = "fanout_order_exchange";
        String routingKey = "";
        // 通过MQ来完成消息的分发
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId);
    }

}
