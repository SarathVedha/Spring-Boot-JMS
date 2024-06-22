package com.vedha.service;

import com.vedha.dto.SampleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageReceiver {

    // Queue listeners is a point-to-point model where only one subscriber can receive the message at a time
    @JmsListener(destination = "${queue.activemq.string}", containerFactory = "queueListenerFactory")
    public void receiveStringMessage(String message) {

        log.warn("Received String message: {}", message);
    }

    @JmsListener(destination = "${queue.activemq.dto}", containerFactory = "queueListenerFactory")
    public void receiveDTOMessage1(SampleDTO message) throws InterruptedException {

        log.warn("Waiting for 10 seconds to simulate a long running process start");
        TimeUnit.SECONDS.sleep(10);
        log.warn("Waiting for 10 seconds to simulate a long running process end");

        log.warn("Received DTO message: {}", message);
    }

    // Topic listeners is a publish-subscribe model where multiple subscribers can receive the same message
    @JmsListener(destination = "${topic.activemq.string}", containerFactory = "topicListenerFactory")
    public void receiveStringTopicMessageOne(String message) {

        log.warn("Received String message from topic1: {}", message);
    }

    @JmsListener(destination = "${topic.activemq.string}", containerFactory = "topicListenerFactory")
    public void receiveStringTopicMessageTwo(String message) {

        log.warn("Received String message from topic2: {}", message);
    }

    @JmsListener(destination = "${topic.activemq.dto}", containerFactory = "topicListenerFactory")
    public void receiveDTOTopicMessageOne(SampleDTO message) {

        log.warn("Received DTO message from topic1: {}", message);
    }

    @JmsListener(destination = "${topic.activemq.dto}", containerFactory = "topicListenerFactory")
    public void receiveDTOTopicMessageTwo(SampleDTO message) {

        log.warn("Received DTO message from topic2: {}", message);
    }
}
