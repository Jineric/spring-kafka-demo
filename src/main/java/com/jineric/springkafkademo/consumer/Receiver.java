package com.jineric.springkafkademo.consumer;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import com.jineric.springkafkademo.model.Car;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "${kafka.topic.json}")
    public void receive(Car car) {
        LOGGER.info("received car='{}'", car.toString());
        latch.countDown();
    }

//    Removed for receiving JSON
//    @KafkaListener(topics = "${kafka.topic.boot}")
//    public void receive(ConsumerRecord<?, ?> consumerRecord) {
//        LOGGER.info("received payload='{}'", consumerRecord.toString());
//        latch.countDown();
//    }
}
