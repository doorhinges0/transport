package com.realtimestudio.transport.storm.activemq;

import static com.realtimestudio.transport.storm.util.Constants.NOTIFICATIONTOPICCONNECTIONURL;
import static com.realtimestudio.transport.storm.util.Constants.NOTIFICATIONTOPICPASSWORD;
import static com.realtimestudio.transport.storm.util.Constants.NOTIFICATIONTOPICUSER;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.realtimestudio.transport.storm.util.Parameters;

public class ActiveMQProducer implements WebsocketMessageBroker {
	private final static Logger LOGGER = LoggerFactory.getLogger(ActiveMQProducer.class);
	private PooledConnectionFactory pooledFactory;
	private static ActiveMQProducer instance = new ActiveMQProducer();
	
	public static ActiveMQProducer getInstance(){
		return instance;
	}
	
	private ActiveMQProducer(){
		System.out.println(Parameters.getProperty(NOTIFICATIONTOPICUSER));
		System.out.println(Parameters.getProperty(NOTIFICATIONTOPICPASSWORD));
		System.out.println(Parameters.getProperty(NOTIFICATIONTOPICCONNECTIONURL));
		
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory (
				Parameters.getProperty(NOTIFICATIONTOPICUSER)
				,Parameters.getProperty(NOTIFICATIONTOPICPASSWORD)
				,Parameters.getProperty(NOTIFICATIONTOPICCONNECTIONURL));
		pooledFactory = new PooledConnectionFactory();
		pooledFactory.setConnectionFactory(connectionFactory);		
	}

	@Override
	public void sendEventToTopic(String event, String topic){
		Connection connection = null;
		try{
			connection = pooledFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			TextMessage message = session.createTextMessage(event);
            Topic messageTopic = session.createTopic(topic);
            MessageProducer producer = session.createProducer(messageTopic);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.send(message);   
		}
		catch(Exception e){
			throw new RuntimeException("Failed to send message to ActiveMQ ", e);
		}
		finally{
			if(connection != null)
				try {
					connection.close();
				} catch (JMSException e) {
					LOGGER.error("Failed to close the connection", e);
				}
		}
     

	}
	
	public static void main(String[] args) throws JMSException{
		ActiveMQProducer producer = new  ActiveMQProducer();
		producer.sendEventToTopic(null, null);
	}

}
