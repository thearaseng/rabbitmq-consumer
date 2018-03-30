package com.theara.rabbitmq.rabbitmqconsumer.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author Theara Seng
 * created on Mar 30, 2018
 */

@Component("loggingMessageListener")
public class LoggingMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("The logging message is : " + message);
    }
}
