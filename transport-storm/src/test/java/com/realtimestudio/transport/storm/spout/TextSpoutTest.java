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

public class TextSpoutTest {

	@Test
	public void test() throws InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("text_spout", new TextSpout());
		builder.setBolt("printbolt", new PrintBolt()).shuffleGrouping("text_spout");
		Config conf = new Config();
		StormRunner.runTopologyLocally(builder.createTopology(), "testtopologgy", conf, 60);
	}

}
