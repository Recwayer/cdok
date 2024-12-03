package org.oriented.rabbitmq.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    public static final String QUEUE_NAME = "firstQueue";
    public static final String EXCHANGE_NAME = "firstExchange";

    public static final String KEY = "my.key";

    @Bean
    public Queue myQueue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    Exchange exchange() {
        return new TopicExchange(EXCHANGE_NAME, false, false);
    }

    @Bean
    Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(KEY).noargs();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
