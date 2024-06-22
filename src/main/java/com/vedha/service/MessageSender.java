package com.vedha.service;

import com.vedha.dto.SampleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSender {

    private final JmsTemplate jmsTemplate;

    @Value("${queue.activemq.string}")
    private String stringQueueName;

    @Value("${queue.activemq.dto}")
    private String dtoQueueName;

    @Value("${topic.activemq.string}")
    private String stringTopicName;

    @Value("${topic.activemq.dto}")
    private String dtoTopicName;

    public void sendMessage(String message) {

        log.warn("Sending String message: {}", message);

        jmsTemplate.convertAndSend(stringQueueName, message);
    }

    /*
        * The DTO object is sent as a JSON string to the ActiveMQ queue.
        * The DTO object is serialized to a JSON string using the Jackson library.
        * In ActiveMQ console, Send a message need to add header as: _type=com.vedha.dto.SampleDTO to send a DTO object.
     */
    public void sendDTOMessage(SampleDTO dto) {

        log.warn("Sending DTO message: {}", dto);

        jmsTemplate.convertAndSend(dtoQueueName, dto);
    }

    public void sendTopicMessage(String message) {

        log.warn("Sending String message to topic: {}", message);

        jmsTemplate.setPubSubDomain(true); // Set to true for topics, false for queues
        jmsTemplate.convertAndSend(stringTopicName, message);
    }

    public void sendDTOTopicMessage(SampleDTO dto) {

        log.warn("Sending DTO message to topic: {}", dto);

        jmsTemplate.setPubSubDomain(true); // Set to true for topics, false for queues
        jmsTemplate.convertAndSend(dtoTopicName, dto);
    }
}
