package ru.andreev.exchange.jms.config;

import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.andreev.exchange.jms.config.properties.JMSProperties;

@Configuration
@RequiredArgsConstructor
public class JmsConfiguration {

    private final JMSProperties jmsProperties;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue(jmsProperties.getRequestQueue());
    }
}