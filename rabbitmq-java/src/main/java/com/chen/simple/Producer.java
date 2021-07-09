package com.chen.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @ClassName Producer
 * @Description TODO
 * @Author xiaochen
 * @Date 2021/6/5 6:52
 */
public class Producer {
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
            String queueName = "queuel";

            //queue, durable, exclusive, autoDelete, arguments
            /**
             * @parame1 队列名称
             * @parame2 是否持久化durable=false 所谓持久化消息是否存盘，
             * 如果false 非持久化true是持久化？非持久化会存盘，但是关闭服务时就没了
             * @parame3 排他性，是否独占独立
             * @parame4 是否自动删除，随着最后一个消费者消息完毕以后是否把队列自动删除，false代表不自动删除
             * @parame5 携带附属参数
             */
            channel.queueDeclare(queueName, false, false, false, null);
            //5: 准备消息内容
            String message = "Hello xiaochen";
            //6: 发送消息给队列 queue
            //虽然没有指定交换机，但是它会给它默认的交换机
            channel.basicPublish("", queueName, null, message.getBytes());

            System.out.println("消息发送成功");
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
