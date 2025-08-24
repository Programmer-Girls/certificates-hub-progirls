package com.nataliatsi.certificatesgenerator.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Input (ms1 → ms2)
    @Value("${app.rabbitmq.exchange}")
    private String inputExchangeName;

    @Value("${app.rabbitmq.routing-key}")
    private String inputRoutingKey;

    @Value("${app.rabbitmq.queue}")
    private String inputQueueName;

    // Output (ms2 → ms3)
    @Value("${app.rabbitmq.out.exchange}")
    private String outputExchangeName;

    @Value("${app.rabbitmq.out.routing-key}")
    private String outputRoutingKey;

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    // Input config
    @Bean
    public DirectExchange inputExchange() {
        return new DirectExchange(inputExchangeName, true, false);
    }

    @Bean
    public Queue inputQueue() {
        return new Queue(inputQueueName, true);
    }

    @Bean
    public Binding inputBinding() {
        return BindingBuilder.bind(inputQueue()).to(inputExchange()).with(inputRoutingKey);
    }

    // Output config
    @Bean
    public TopicExchange outputExchange() {
        return new TopicExchange(outputExchangeName, true, false);
    }

}
