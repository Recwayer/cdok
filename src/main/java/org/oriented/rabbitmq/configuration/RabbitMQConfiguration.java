package org.oriented.rabbitmq.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    public static final String EMAIL_QUEUE = "emailQueue";
    public static final String EMAIL_EXCHANGE = "emailExchange";

    public static final String EMAIL_KEY = "email.key";

    public static final String WEB_QUEUE = "webQueue";
    public static final String WEB_EXCHANGE = "webExchange";

    public static final String WEB_KEY = "web.key";

    public static final String COST_QUEUE = "costQueue";

    public static final String COST_EXCHANGE = "costExchange";

    public static final String COST_KEY = "cost.key";

    public static final String TOTAL_COST_QUEUE = "totalCostQueue";

    public static final String TOTAL_COST_EXCHANGE = "totalCostQueue";

    public static final String TOTAL_COST_KEY = "totalCostQueue";


    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, false);
    }

    @Bean
    Exchange emailExchange() {
        return new TopicExchange(EMAIL_EXCHANGE, false, false);
    }

    @Bean
    Binding emailBinding(Queue emailQueue, Exchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with(EMAIL_KEY).noargs();
    }

    @Bean
    public Queue webQueue() {
        return new Queue(WEB_QUEUE, false);
    }

    @Bean
    Exchange webExchange() {
        return new TopicExchange(WEB_EXCHANGE, false, false);
    }

    @Bean
    Binding webBinding(Queue webQueue, Exchange webExchange) {
        return BindingBuilder.bind(webQueue).to(webExchange).with(WEB_KEY).noargs();
    }

    @Bean
    public Queue costQueue() {
        return new Queue(COST_QUEUE, false);
    }

    @Bean
    Exchange costExchange() {
        return new TopicExchange(COST_EXCHANGE, false, false);
    }

    @Bean
    Binding costBinding(Queue costQueue, Exchange costExchange) {
        return BindingBuilder.bind(costQueue).to(costExchange).with(COST_KEY).noargs();
    }

    @Bean
    public Queue totalCostQueue() {
        return new Queue(TOTAL_COST_QUEUE, false);
    }

    @Bean
    Exchange totalCostExchange() {
        return new TopicExchange(TOTAL_COST_EXCHANGE, false, false);
    }

    @Bean
    Binding totalCostBinding(Queue totalCostQueue, Exchange totalCostExchange) {
        return BindingBuilder.bind(totalCostQueue).to(totalCostExchange).with(TOTAL_COST_KEY).noargs();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}
