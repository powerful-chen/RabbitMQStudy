package com.chen.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @ClassName Producer
 * @Description TODO
 * @Author xiaochen
 * @Date 2021/6/5 6:52
 */
// 主题模式
public class Producer {
    public static void main(String[] args) {
        // 所有的中间件技术都是基于tcp/ip协议基础之上构建新型的协议规范，只不过是rabbitmq遵循的是amqp
        //1: 创建连接工程
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.61.101");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");

        Connection connection = null;
        Channel channel = null;

        try {
            //2: 创建连接 connection
            connection = connectionFactory.newConnection("生产者");
            //3：通过连接获取通道 channel
            channel = connection.createChannel();
            //4: 准备消息内容
            String message = "Hello xiaochen";
            //5：准备交换机
            String exchangeName = "topic_exchange";
            // 定义路由key
            String routeKey = "com.order.user.test.xxxx";
            // 指定交换机的类型
            String type = "topic";

            //6: 发送消息给队列 queue

            channel.basicPublish(exchangeName, routeKey, null, message.getBytes());
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
