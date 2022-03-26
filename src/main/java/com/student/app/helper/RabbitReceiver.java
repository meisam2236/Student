package com.student.app.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "hello")
public class RabbitReceiver {
    private static final Logger log = LoggerFactory.getLogger(GradeLogger.class);
    @RabbitHandler
    public void receive(String in) {
        log.info(in);
    }
}
