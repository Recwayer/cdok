package org.oriented.rabbitmq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.oriented.rabbitmq.configuration.RabbitMQConfiguration;
import org.service_oriented.rabitmq.model.EmailAction;
import org.service_oriented.rabitmq.model.EmailMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessageEmailProducer {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public MessageEmailProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Async
    public void sendMessage(Object object, EmailAction action) {
        String message = parseMessage(object, action);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EMAIL_QUEUE, message);
    }

    @Async
    public void sendMessage(Class<?> entityType, Object object, EmailAction action) {
        String message = parseMessage(entityType, object, action);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EMAIL_QUEUE, message);
    }

    private String parseMessage(Object message, EmailAction action) {
        try {
            return objectMapper.writeValueAsString(EmailMessage.builder().action(action.name()).payload(message).build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String parseMessage(Class<?> entityType, Object message, EmailAction action) {
        try {
            return objectMapper.writeValueAsString(EmailMessage.builder().entity(entityType.getSimpleName()).action(action.name()).payload(message).build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
