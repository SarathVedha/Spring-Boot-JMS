package com.vedha.config;

import jakarta.jms.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Slf4j
@Configuration
public class AppConfiguration {

    // Queue listener factory bean to listen to the ActiveMQ queue
    @Bean
    JmsListenerContainerFactory<?> queueListenerFactory(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory,
                                                        MessageConverter messageConverter) {

        DefaultJmsListenerContainerFactory listenerContainerFactory = new DefaultJmsListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(connectionFactory);
        listenerContainerFactory.setMessageConverter(messageConverter);
        listenerContainerFactory.setPubSubDomain(false); // Set to true for topics, false for queues
        listenerContainerFactory.setConcurrency("1-5"); // Set the number of concurrent consumers
        listenerContainerFactory.setErrorHandler(t -> log.error("An error occurred in the JMS Queue listener: ", t));

        return listenerContainerFactory;
    }

    // Topic listener factory bean to listen to the ActiveMQ topic
    @Bean
    JmsListenerContainerFactory<?> topicListenerFactory(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory,
                                                        MessageConverter messageConverter) {

        DefaultJmsListenerContainerFactory listenerContainerFactory = new DefaultJmsListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(connectionFactory);
        listenerContainerFactory.setMessageConverter(messageConverter);
        listenerContainerFactory.setPubSubDomain(true); // Set to true for topics, false for queues
        listenerContainerFactory.setErrorHandler(t -> log.error("An error occurred in the JMS Topic listener: ", t));
        // for topic currency is not required as it is a publish-subscribe model

        return listenerContainerFactory;
    }

    // MessageConverter bean to convert messages to JSON format and vice versa
    @Bean
    MessageConverter messageConverter() {

        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
//        messageConverter.setTargetType(MessageType.TEXT);
        messageConverter.setTypeIdPropertyName("_type");

        return messageConverter;
    }

    // JmsTemplate bean to send messages to the ActiveMQ queue
    @Bean
    JmsTemplate jmsTemplate(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory,
                            MessageConverter messageConverter) {

        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setMessageConverter(messageConverter);

        return jmsTemplate;
    }
}
