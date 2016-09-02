package com.realtimestudio.transport.storm.spout;

import org.junit.Test;

import backtype.storm.Config;
import backtype.storm.topology.TopologyBuilder;

import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.realtimestudio.transport.model.baisha.RoutePoint;
import com.realtimestudio.transport.storm.bolt.PrintBolt;
import com.realtimestudio.transport.storm.spout.GPSSignalSpout;
import com.realtimestudio.transport.storm.util.Constants;
import com.realtimestudio.transport.storm.util.StormRunner;

public class BaiShaGPSSignalSpoutTest {

	@Test
	public void test() throws InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(Constants.KAFKA_SPOUT_ID , new GPSSignalSpout());
		builder.setBolt("printbolt", new PrintBolt()).shuffleGrouping(Constants.KAFKA_SPOUT_ID);
		Config conf = new Config();
		conf.registerSerialization(RoutePoint.class, FieldSerializer.class);
		StormRunner.runTopologyLocally(builder.createTopology(), "testtopologgy", conf, 60);
	}

}
