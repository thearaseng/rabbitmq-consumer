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

    public static final String ROUTING_KEY = "my.queue.key";

    @Bean
    Queue queue() {
        return new Queue(ROUTING_KEY, true);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("my_queue_exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(ROUTING_KEY);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(ROUTING_KEY);
        container.setMessageListener(messageListenerAdapter);

        return container;
    }

    @Bean
    public MessageListenerAdapter myQueueListener(@Qualifier("messageListenerImpl") MessageListener listener) {
        return new MessageListenerAdapter(listener, "onMessage");
    }

}
