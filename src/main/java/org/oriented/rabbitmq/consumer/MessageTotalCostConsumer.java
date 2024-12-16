package org.oriented.rabbitmq.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.oriented.rest.api.service.OrderService;
import org.service_oriented.rabitmq.model.TotalCostMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageTotalCostConsumer {
    private OrderService orderService;
    private final ObjectMapper objectMapper;

    public MessageTotalCostConsumer(OrderService orderService, ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = "#{@rabbitMQConfiguration.TOTAL_COST_QUEUE}")
    public void receiveMessage(String message) {
        TotalCostMessage totalCostMessage = mapToObject(message);
        orderService.updateTotalCost(totalCostMessage.getOrderId(), totalCostMessage.getTotalCost());
    }

    private TotalCostMessage mapToObject(String message) {
        try {
            return objectMapper.readValue(message, TotalCostMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
