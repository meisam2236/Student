package com.student.app.helper;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Value("${rabbitapi.queue}")
    String queue_name;
    @Bean
    public Queue hello() {
        return new Queue(queue_name);
    }

    @Bean
    public RabbitReceiver receiver() {
        return new RabbitReceiver();
    }
}
