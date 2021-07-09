package com.chen.service.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName FanoutDuanXinConsumer
 * @Description TODO
 * @Author xiaochen
 * @Date 2021/7/5 10:26
 */
@Component
@RabbitListener(queues = {"email.fanout.queue"})
public class FanoutEmailConsumer {

    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("email fanout---接收到了订单信息是: ->" + message);
    }
}
