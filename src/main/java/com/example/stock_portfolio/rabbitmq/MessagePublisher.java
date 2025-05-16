package com.example.stock_portfolio.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class MessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(MessagePublisher.class);

    @Value("${app.rabbitmq.exchange-name}")
    private String exchangeName;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String messageContent) {
        CustomMessage customMessage = new CustomMessage(
                UUID.randomUUID().toString(),
                messageContent,
                new Date()
        );
        try {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, customMessage);
            logger.info("Message sent to RabbitMQ. Exchange: {}, RoutingKey: {}, Message: {}", exchangeName, routingKey, customMessage);
        } catch (Exception e) {
            logger.error("Error sending message to RabbitMQ: {}", e.getMessage(), e);
        }
    }

    public void sendCustomMessage(CustomMessage message) {
        try {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
            logger.info("CustomMessage sent to RabbitMQ. Exchange: {}, RoutingKey: {}, Message: {}", exchangeName, routingKey, message);
        } catch (Exception e) {
            logger.error("Error sending CustomMessage to RabbitMQ: {}", e.getMessage(), e);
        }
    }
}
