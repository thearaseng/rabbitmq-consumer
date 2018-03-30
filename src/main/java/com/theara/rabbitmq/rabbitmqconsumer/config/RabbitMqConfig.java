package com.theara.rabbitmq.rabbitmqconsumer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Theara Seng
 * created on Mar 30, 2018
 */

@Configuration
public class RabbitMqConfig {

    public static final String ROUTING_KEY = "server1.app1.module1.info";
    public static final String ERROR_ROUTING_KEY = "server1.app1.module1.error";

    @Bean("infoQueue")
    Queue infoQueue() {
        return new Queue(ROUTING_KEY, true);
    }

    @Bean("errorQueue")
    Queue errorQueue() {
        return new Queue(ERROR_ROUTING_KEY, true);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("my_queue_exchange");
    }

    @Bean
    Binding infoQueueBinding(@Qualifier("infoQueue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(ROUTING_KEY);
    }

    @Bean
    Binding errorQueueBinding(@Qualifier("errorQueue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(ERROR_ROUTING_KEY);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, @Qualifier("infoMessageListener") MessageListener listener) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(ROUTING_KEY);
        container.setMessageListener(new MessageListenerAdapter(listener, "onMessage"));

        return container;
    }

    @Bean
    public SimpleMessageListenerContainer errorMessageListenerContainer(ConnectionFactory connectionFactory, @Qualifier("errorMessageListener") MessageListener listener) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(ERROR_ROUTING_KEY);
        container.setMessageListener(new MessageListenerAdapter(listener, "onMessage"));

        return container;
    }

}
