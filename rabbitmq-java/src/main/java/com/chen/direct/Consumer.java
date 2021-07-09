package com.chen.direct;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @ClassName Consumer
 * @Description TODO
 * @Author xiaochen
 * @Date 2021/6/5 6:53
 */
public class Consumer {
    public static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //1: 创建连接工程
            ConnectionFactory connectionFactory = new ConnectionFactory();
            //2、设置连接属性
            connectionFactory.setHost("192.168.61.101");
            connectionFactory.setPort(5672);
            connectionFactory.setVirtualHost("/");
            connectionFactory.setUsername("admin");
            connectionFactory.setPassword("admin");
            // 获取队列的名称
            final String queueName = Thread.currentThread().getName();
            Connection connection = null;
            Channel channel = null;
            try {
                //2: 创建连接 connection
                connection = connectionFactory.newConnection("生产者");
                //3：通过连接获取通道 channel
                channel = connection.createChannel();
                //4、定义接收消息的回调
                Channel finalChannel = channel;
                finalChannel.basicConsume(queueName, new DeliverCallback() {
                    @Override
                    public void handle(String s, Delivery delivery) throws IOException {
                        System.out.println(delivery.getEnvelope().getDeliveryTag());
                        System.out.println(queueName + ": 收到消息是：" + new String(delivery.getBody(), "UTF-8"));
                    }
                }, new CancelCallback() {
                    @Override
                    public void handle(String s) throws IOException {

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
    };

    public static void main(String[] args) {
        // 启动三个线程去执行
        new Thread(runnable, "quene1").start();
        new Thread(runnable, "quene2").start();
        new Thread(runnable, "quene3").start();
    }
}
