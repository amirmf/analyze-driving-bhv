package com.taraan.dum.logger;

import com.taraan.dum.logger.kafka.KafkaConsumerConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(KafkaConsumerConfig.class);
    }
}
