
package com.csc.rabbitmqdemo.template.send;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send() {
        String msg1 = "I am direct.a msg  =======";
        System.out.println("sender1 : " + msg1);
        rabbitTemplate.convertAndSend("directExchange", "direct.a", msg1);
        
        String msg2 = "I am direct.b msg  =======";
        System.out.println("sender2 : " + msg2);
        rabbitTemplate.convertAndSend("directExchange", "direct.b", msg2);
        
        String msg3 = "I am direct.c msg  =======";
        System.out.println("sender3 : " + msg3);
        rabbitTemplate.convertAndSend("directExchange", "direct.c", msg3);
    }

}
