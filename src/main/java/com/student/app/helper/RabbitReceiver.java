package com.student.app.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Slf4j
@RabbitListener(queues = "hello")
public class RabbitReceiver {
    @RabbitHandler
    public void receive(String in) {
        log.info(in);
    }
}
