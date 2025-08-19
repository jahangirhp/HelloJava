package com.example;

import com.rabbitmq.client.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RabbitConsumer {

    private final static String QUEUE_NAME = "messages";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Match Symfony’s declaration (durable = true)
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            System.out.println(" [*] Waiting for messages in queue: " + QUEUE_NAME);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                System.out.println(" [" + time + "] Received: " + message);
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
            
            // Keep program running
            synchronized (RabbitConsumer.class) {
                RabbitConsumer.class.wait();
            }
        }
    }
}
