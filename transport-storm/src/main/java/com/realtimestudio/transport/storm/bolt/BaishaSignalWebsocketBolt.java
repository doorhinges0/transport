package com.realtimestudio.transport.storm.bolt;

import java.util.Map;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.realtimestudio.transport.model.baisha.RouteTrack;
import com.realtimestudio.transport.storm.activemq.ActiveMQProducer;
import com.realtimestudio.transport.storm.activemq.WebsocketMessageBroker;
import com.realtimestudio.transport.storm.util.Constants;
import com.realtimestudio.transport.storm.util.Parameters;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class BaishaSignalWebsocketBolt extends BaseBasicBolt{
	private static final Logger LOGGER = LoggerFactory.getLogger(BaishaSignalWebsocketBolt.class);
	private static final long serialVersionUID = 4799635783406441430L;
	
	private WebsocketMessageBroker broker;
	
	@Override
	public void prepare(Map conf, TopologyContext context){
		broker = ActiveMQProducer.getInstance();
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		RouteTrack track = (RouteTrack) input.getValueByField(Constants.TRACKPOINTFIELD);
		try {
			broker.sendEventToTopic(track.toString(), Parameters.getProperty(Constants.NOTIFICATIONTOPICALLSIGNAL));
		} catch (JMSException e) {
			LOGGER.error("Failed to send message to broker", e);
		}		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

}
