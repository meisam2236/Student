package com.student.app.config;

import com.student.app.helper.RabbitReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloRabbitConfig {
    @Bean
    public RabbitReceiver receiver() {
        return new RabbitReceiver();
    }
}
