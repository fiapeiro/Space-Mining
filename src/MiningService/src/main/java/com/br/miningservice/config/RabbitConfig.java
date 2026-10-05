package com.br.miningservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE_NAME = "command.queue";
    public static final String EXCHANGE_NAME = "command.exchange";
    public static final String ROUTING_KEY = "command.key";

    // queue
    @Bean
    Queue queue(){
        return new Queue(QUEUE_NAME, true);
    }

    // exchange
    @Bean
    TopicExchange exchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    // binding
    @Bean
    Binding binding(){
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY);
    }
}
