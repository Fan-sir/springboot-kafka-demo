package com.xk.springbootkafkademo.controller;

import com.alibaba.fastjson.JSON;
import com.xk.springbootkafkademo.pojo.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/kafka")
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/send")
    public void send() {
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setSendTime(new Date());
        message.setMessage("这是一个测试消息");
        logger.info("--------------- message = {}", JSON.toJSONString(message));
        //topic为test
        kafkaTemplate.send("test", JSON.toJSONString(message));
    }


    @KafkaListener(topics = {"test"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            logger.info("====>接收到kafka消息： topic = {}, offset = {} \n", record.topic(), record.offset());
            logger.info("kafka message:{}", message);
        }
    }
}
