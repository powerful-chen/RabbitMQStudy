package com.chen.all;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @ClassName Producer
 * @Description TODO
 * @Author xiaochen
 * @Date 2021/6/5 6:52
 */
// 自动创建交换机和队列
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
            String message = "xiaochen is very nice，I love huangjiechun！";
            //5：准备交换机
            String exchangeName = "direct_message_exchange";
            // 交换机的类型 direct/topic/fanout/headers
            String exchangeType  ="direct";
            // 如果你用界面把queue 和 exchange的关系先绑定的话，代码就不需要编写这些声明代码可以让代码变得更加简洁，但是不容易读懂
            // 下面用代码声明的方式进行
            // 声明交换机 持久化是指交换机会不会随着服务器的重启造成数据丢失
            channel.exchangeDeclare(exchangeName,exchangeType,true);

            // 声明队列
            channel.queueDeclare("queue5",true,false,false,null);
            channel.queueDeclare("queue6",true,false,false,null);
            channel.queueDeclare("queue7",true,false,false,null);
            // 绑定队列和交换机的关系
            channel.queueBind("queue5",exchangeName,"order");
            channel.queueBind("queue6",exchangeName,"order");
            channel.queueBind("queue7",exchangeName,"course");

            channel.basicPublish(exchangeName, "course", null, message.getBytes());
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
                    System.out.println("消息发送出现异常");
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
