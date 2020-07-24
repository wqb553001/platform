package com.doctor.assistant.ImageRecognition.MQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 * 通过注入 AmqpTemplate接口的实例来实现消息的发送，
 * AmqpTemplate接口定义了一套针对AMQP协议的基础操作。
 *
 * 在该生产者，我们会产生一个字符串，并发送到名为 hello的队列中。
 */
@Component
public class Sender {
 
    @Autowired
    private AmqpTemplate amqpTemplate;
 
    public void send() {
        String context = "hello " + new Date();
        System.out.println("sender: " + context);
        this.amqpTemplate.convertAndSend("hello",context);
    }

    public void sendMessage(String context) {
        System.out.println("sender: " + context);
        this.amqpTemplate.convertAndSend("context",context);
    }
}
