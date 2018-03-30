package com.theara.rabbitmq.rabbitmqconsumer.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author Theara Seng
 * created on Mar 30, 2018
 */

@Component("errorMessageListener")
public class ErrorMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("The error message is : " + message);
    }
}
