package com.realtimestudio.transport.storm.activemq;

import javax.jms.JMSException;

import org.junit.Test;

public class ActiveMQProducerTest {
	
	@Test
	public void test() throws JMSException{
		WebsocketMessageBroker broker = ActiveMQProducer.getInstance();
		broker.sendEventToTopic("This is a  test", "test11");
		
				
	}

}
