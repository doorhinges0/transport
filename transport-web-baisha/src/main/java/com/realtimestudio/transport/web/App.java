package com.realtimestudio.transport.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.realtimestudio.transport.streaming.SignalCollector;
import com.realtimestudio.transport.streaming.impl.KafkaSignalCollector;

@Configuration
@ComponentScan("com.realtimestudio.transport")
@EnableAutoConfiguration
public class App {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
	public static void main (String[] args){
		LOGGER.info("Transport web is starting...");
		SpringApplication.run(App.class, args);
	}
	
	//config for kafka collector 
	@Bean(name="kafkaSignalCollector")
	@Lazy
	public SignalCollector kafkaSignalCollector(@Value("${kafka.brokerlist}") String brokerList, @Value("${kafka.topic}") String topic){
		return new KafkaSignalCollector(brokerList, topic);
	}

}
