package com.yupi.springbootinit.bizmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.yupi.springbootinit.config.RabbitConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @description：用于测试，在程序执行前执行一次
 * @author： songgt
 * @create： 2024/7/29 下午10:24
 */
@Component
public class BiInitMain {

    @Resource
    private RabbitConfig rabbitConfig;

    @PostConstruct
    public void init() {
        try {
            ConnectionFactory connectionFactory = rabbitConfig.connectionFactory();
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            String EXCHANGE_NAME = BiMqConstant.BI_EXCHANGE_NAME;
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            String queueName = BiMqConstant.BI_QUEUE_NAME;
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, BiMqConstant.BI_ROUTING_KEY);
        } catch (Exception e) {
        }
    }
}
