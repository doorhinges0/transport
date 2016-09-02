package com.realtimestudio.transport.event.simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.realtimestudio.transport.event.simulation.emitter.SignalEmitter;
import com.realtimestudio.transport.event.simulation.emitter.impl.SignalEmitterImpl;
import com.realtimestudio.transport.httpclient.impl.HttpSignalCollector;
import com.realtimestudio.transport.streaming.SignalCollector;
import com.realtimestudio.transport.streaming.impl.KafkaSignalCollector;

@Configuration
@ComponentScan("com.realtimestudio.transport")
@EnableAutoConfiguration

public class App {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
	private static final String SIGNALEMITTERDEFAULTBEANNAME = "httpSignalEmitter";
	
	public static void main(String[] args) {
		String beanName = (args==null || args.length<1) ? SIGNALEMITTERDEFAULTBEANNAME : args[0];

		ApplicationContext ctx = SpringApplication.run(App.class, args);
		SignalEmitter emitter = (SignalEmitter) ctx.getBean(beanName);
		LOGGER.info(beanName + " is intialized");
		emitter.emit();
	}
	
	//config for http client collector
	@Bean(name="httpSignalCollector")
	@Lazy
	public SignalCollector httpSignalCollector(@Value("${http.endpoint}") String endPoint){
		return new HttpSignalCollector(endPoint);
	}
	
	@Bean(name="httpSignalEmitter")
	@Lazy
	@Autowired
	public SignalEmitter httpSignalEmitter(@Qualifier("httpSignalCollector") SignalCollector signalCollector
			, @Value("${routes.dir}") String fileDirectory, @Value("${simulation.interval}") int interval){
		return new SignalEmitterImpl(fileDirectory, signalCollector, interval);
				
	}
	
	//config for kafka collector 
	@Bean(name="kafkaSignalCollector")
	@Lazy
	public SignalCollector kafkaSignalCollector(@Value("${kafka.brokerlist}") String brokerList, @Value("${kafka.topic}") String topic){
		return new KafkaSignalCollector(brokerList, topic);
	}
	
	@Bean(name="kafkaSignalEmitter")
	@Lazy
	@Autowired
	public SignalEmitter kafkaSignalEmitter(@Qualifier("kafkaSignalCollector") SignalCollector signalCollector
			, @Value("${routes.dir}") String fileDirectory, @Value("${simulation.interval}") int interval){
		return new SignalEmitterImpl(fileDirectory, signalCollector, interval);
				
	}
	
	//config for standard out for test purpose
	@Bean(name="stdoutCollector")
	@Lazy
	public SignalCollector stdoutCollector(){
		return new SignalCollector(){

			@Override
			public void send(String signal) {
				System.out.println(signal);
				
			}

			@Override
			public void send(String key, String signal) {
				System.out.println("key=" + key + "; signal=" + signal);
				
			}

			@Override
			public void close() {
				System.out.println("collector is closed");
				
			}
			
		};
	}
	
	@Bean(name="stdoutSignalEmitter")
	@Lazy
	@Autowired
	public SignalEmitter stdoutSignalEmitter(@Qualifier("stdoutCollector") SignalCollector signalCollector
			, @Value("${routes.dir}") String fileDirectory, @Value("${simulation.interval}") int interval){
		return new SignalEmitterImpl(fileDirectory, signalCollector, interval);
				
	}


}
