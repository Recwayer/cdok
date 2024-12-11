package org.oriented.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.oriented.rabbitmq.configuration.RabbitMQConfiguration;
import org.service_oriented.dto.PickupPointDTO;
import org.service_oriented.rabitmq.model.PickUpPointAddedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessageWebProducer {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public MessageWebProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Async
    public void sendMessage(PickupPointDTO pickupPoint) {
        String message = parseMessage(pickupPoint);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.WEB_QUEUE, message);
    }

    private String parseMessage(PickupPointDTO pickupPoint) {
        try {
            return objectMapper.writeValueAsString(PickUpPointAddedMessage.builder().name(pickupPoint.name()).address(pickupPoint.address()).workingHours(pickupPoint.workingHours()).build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
