package com.realtimestudio.transport.storm.activemq;

import static org.junit.Assert.*;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class ActiveMQConsumer {

	@Test
	public void testNonDurable() throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin",
				"tcp://10.10.81.168:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic1 = session.createTopic("all_signal");
		MessageConsumer consumer = session.createConsumer(topic1);

		for (int i = 0; i < 10; i++) {
			Message message = consumer.receive();
			System.out.println("message");
			System.out.println(((TextMessage) message).getText());
		}
		connection.close();

	}

	@Test
	public void testDurable() throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin",
				"tcp://10.10.81.168:61616");
		Connection connection = connectionFactory.createConnection();
		connection.setClientID("durable subscriber");
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic1 = session.createTopic("test11");
		MessageConsumer consumer = session.createDurableSubscriber(topic1, "subscriptionName");
		connection.start();
		Message message = consumer.receive();
		System.out.println(((TextMessage) message).getText());
		consumer.close();
		session.unsubscribe("subscriptionName");
		connection.close();

	}

}
