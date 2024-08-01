package com.yupi.springbootinit.bizmq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description：RabbitMQ 消费者代码
 * @author： songgt
 * @create： 2024/7/29 下午10:08
 */
@Component
@Slf4j
public class MyMessageConsumer {

    @RabbitListener(queues = {"test_queue"}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG)long deliveryTag) throws Exception {
        log.info("receive message: {}", message);
        channel.basicAck(deliveryTag, false);
    }
}
