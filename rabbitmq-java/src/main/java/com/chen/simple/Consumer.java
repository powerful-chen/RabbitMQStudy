package com.chen.simple;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @ClassName Consumer
 * @Description TODO
 * @Author xiaochen
 * @Date 2021/6/5 6:53
 */
public class Consumer {
    public static void main(String[] args) {
        // 所有的中间件技术都是基于tcp/ip协议基础之上构建新型的协议规范，只不过是rabbitmq遵循的是amqp
        //1: 创建连接工程
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.61.101");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");

        Connection connection = null;
        Channel channel = null;

        try {
            //2: 创建连接 connection
            connection = connectionFactory.newConnection("生产者");
            //3：通过连接获取通道 channel
            channel = connection.createChannel();
            //4：通过创建交换机，声明队列，绑定关系，路由key，发送消息，和接收消息

            channel.basicConsume("queuel", true, new DeliverCallback() {
                @Override
                public void handle(String s, Delivery message) throws IOException {
                    System.out.println("收到消息是" + new String(message.getBody(), "UTF-8"));
                }
            }, new CancelCallback() {
                @Override
                public void handle(String s) throws IOException {
                    System.out.println("接收失败了");
                }
            });
            System.out.println("开始接受消息");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //7、关闭连接
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //7、关闭连接
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
