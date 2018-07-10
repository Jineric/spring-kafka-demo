package com.jineric.springkafkademo.producer;

import com.jineric.springkafkademo.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

public class Sender {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Value("${kafka.topic.json}")
    private String jsonTopic;

    //    Changed String to Car for JSON
    @Autowired
    private KafkaTemplate<String, Car> kafkaTemplate;

    public void send(Car car) {
        LOGGER.info("sending car='{}'", car.toString());
        kafkaTemplate.send(jsonTopic, car);
    }

//    Removed for sending JSON
//    public void send(String topic, String payload){
//        LOGGER.info("sending payload='{}' to topic='{}'", payload, topic);
//        kafkaTemplate.send(topic, payload);
//    }
}