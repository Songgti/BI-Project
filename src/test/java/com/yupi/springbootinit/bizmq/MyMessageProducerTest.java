package com.yupi.springbootinit.bizmq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description：TODO
 * @author： songgt
 * @create： 2024/7/29 下午10:41
 */
@SpringBootTest
class MyMessageProducerTest {

    @Resource
    private MyMessageProducer myMessageProducer;

    @Test
    void sendMessage(){
        myMessageProducer.sendMessage("test_exchange", "test_routingKey", "你好");
    }
}
