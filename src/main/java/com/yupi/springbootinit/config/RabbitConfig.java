package com.yupi.springbootinit.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description：TODO
 * @author： songgt
 * @create： 2024/8/4 上午10:40
 */
@Configuration
public class RabbitConfig {

    @Bean
    public ConnectionFactory connectionFactory () {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        return connectionFactory;
    }
}
