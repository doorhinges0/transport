package com.realtimestudio.transport.storm.bolt;

import static org.junit.Assert.*;

import org.junit.Test;

import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.realtimestudio.transport.model.baisha.RoutePoint;
import com.realtimestudio.transport.model.baisha.RouteTrack;
import com.realtimestudio.transport.storm.spout.GPSSignalSpout;
import com.realtimestudio.transport.storm.util.Constants;
import com.realtimestudio.transport.storm.util.StormRunner;

import backtype.storm.Config;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class BaishaSignalProcessBoltTest {

	@Test
	public void test() throws InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(Constants.KAFKA_SPOUT_ID , new GPSSignalSpout());
		builder.setBolt(Constants.BAISHASIGNALPROCESSBOLT, new BaishaSignalProcessBolt()).fieldsGrouping(Constants.KAFKA_SPOUT_ID, new Fields(Constants.ID));
		builder.setBolt("printbolt", new PrintBolt()).shuffleGrouping(Constants.BAISHASIGNALPROCESSBOLT);
		Config conf = new Config();
		conf.registerSerialization(RoutePoint.class, FieldSerializer.class);
		conf.registerSerialization(RouteTrack.class, FieldSerializer.class);
		StormRunner.runTopologyLocally(builder.createTopology(), "testtopologgy", conf, 60);
	}

}
