package com.example.stock_portfolio.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(MessageSubscriber.class);

    // Listener for the main queue, refers to the queue name in application.yml via SpEL
    @RabbitListener(queues = "${app.rabbitmq.queue-name}")
    public void receiveMessage(CustomMessage message) {
        logger.info("Received message from RabbitMQ Queue '{}': {}", "${app.rabbitmq.queue-name}", message.toString());
        // Add processing logic here
        // Example: if (message.getMessageContent().contains("error")) throw new RuntimeException("Simulated processing error");
    }

    // Listener for the Dead Letter Queue (DLQ)
    @RabbitListener(queues = "${app.rabbitmq.dead-letter-queue-name}")
    public void receiveDeadLetterMessage(CustomMessage message) {
        logger.warn("Received message from Dead Letter Queue '{}': {}. This message failed processing.", "${app.rabbitmq.dead-letter-queue-name}", message.toString());
        // Add logic for handling dead-lettered messages (e.g., logging, alerting, manual reprocessing)
    }
}
