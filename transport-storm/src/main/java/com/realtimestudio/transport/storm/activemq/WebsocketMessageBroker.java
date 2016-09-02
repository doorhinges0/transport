package com.realtimestudio.transport.storm.activemq;

import javax.jms.JMSException;

public interface WebsocketMessageBroker {
	void sendEventToTopic(String event, String topic) throws JMSException;

}
