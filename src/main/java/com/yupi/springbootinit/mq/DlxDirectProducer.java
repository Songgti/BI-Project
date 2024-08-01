package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

public class DlxDirectProducer {

  private static final String DEAD_EXCHANGE_NAME = "dlx_direct_exchange";
  private static final String EXCHANGE_NAME = "direct2_exchange";


  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    try (Connection connection = factory.newConnection();
         Channel channel = connection.createChannel()) {
        channel.exchangeDeclare(DEAD_EXCHANGE_NAME, "direct");

        String queueName = "boss_dlx_queue";
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, DEAD_EXCHANGE_NAME, "boss");

        String queueName2 = "waibao_dlx_queue";
        channel.queueDeclare(queueName2, true, false, false, null);
        channel.queueBind(queueName2, DEAD_EXCHANGE_NAME, "waibao");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String userInput = scanner.nextLine();
            String[] split = userInput.split(" ");
            if (split.length < 1){
                continue;
            }
            String message = split[0];
            String routingKey = split[1];

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + " routingKey:" + routingKey + "'");
        }
    }
  }
}
