package org.oriented.rabbitmq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.oriented.rabbitmq.configuration.RabbitMQConfiguration;
import org.oriented.rest.api.model.Shipment;

import org.service_oriented.dto.OrderDTO;
import org.service_oriented.rabitmq.model.CostMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageCostProducer {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public MessageCostProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Async
    public void sendMessage(OrderDTO order, List<Shipment> shipments) {
        String message = parseMessage(order, shipments);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.COST_QUEUE, message);
    }

    private String parseMessage(OrderDTO order, List<Shipment> shipments) {
        try {
            return objectMapper.writeValueAsString(CostMessage.builder()
                    .OrderId(order.id())
                    .OrderNumber(order.orderNumber())
                    .trackingNumbers(shipments.stream().map(Shipment::getTrackingNumber).toList())
                    .weights(shipments.stream().map(Shipment::getWeight).toList())
                    .deliveryTypes(shipments.stream().map(s -> s.getDeliveryType().name()).toList())
                    .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
