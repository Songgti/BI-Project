package com.yupi.springbootinit.bizmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @description：用于测试，在程序执行前执行一次
 * @author： songgt
 * @create： 2024/7/29 下午10:24
 */
public class MqInitMain {

    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            String EXCHANGE_NAME = "test_exchange";
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            String queueName = "test_queue";
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, "test_routingKey");
        } catch (Exception e) {
        }
    }
}
