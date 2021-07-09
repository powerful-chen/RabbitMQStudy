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
@RabbitListener(queues = {"duanxin.fanout.queue"})
public class FanoutDuanXinConsumer {

    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("duanxin fanout---接收到了订单信息是: ->" + message);
    }
}
