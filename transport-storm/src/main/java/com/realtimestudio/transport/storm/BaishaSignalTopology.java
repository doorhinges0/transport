package com.realtimestudio.transport.storm;

import static com.realtimestudio.transport.storm.util.Constants.TOPOLOGYSPOUTS;
import static com.realtimestudio.transport.storm.util.Constants.TOPOLOGYWORKERS;
import static com.realtimestudio.transport.utils.StringUtils.parseIntOrDefault;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.realtimestudio.transport.model.baisha.RoutePoint;
import com.realtimestudio.transport.model.baisha.RouteTrack;
import com.realtimestudio.transport.storm.bolt.BaishaSignalProcessBolt;
import com.realtimestudio.transport.storm.bolt.BaishaSignalWebsocketBolt;
import com.realtimestudio.transport.storm.spout.GPSSignalSpout;
import com.realtimestudio.transport.storm.util.Constants;
import com.realtimestudio.transport.storm.util.Parameters;
import com.realtimestudio.transport.storm.util.StormRunner;

import backtype.storm.Config;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class BaishaSignalTopology {
	private static final int spoutsN = parseIntOrDefault(Parameters.getProperty(TOPOLOGYSPOUTS), 1);
	private static final int processBoltsN = parseIntOrDefault(Parameters.getProperty(Constants.TOPOLOGYBSSIGNALPROCESSBOLT), 1);
	private static final int websocketBoltsN = parseIntOrDefault(Parameters.getProperty(Constants.TOPOLOGYBSSIGNALWEBSOCKETBOLT), 1);
	private static final int workersN = parseIntOrDefault(Parameters.getProperty(TOPOLOGYWORKERS), 1);
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaishaSignalTopology.class);
	
	public StormTopology buildLocalTopology(){
		TopologyBuilder builder = new TopologyBuilder();
		LOGGER.info("Setting " + Constants.KAFKA_SPOUT_ID);
		builder.setSpout(Constants.KAFKA_SPOUT_ID , new GPSSignalSpout());
		LOGGER.info("Setting " + Constants.BAISHASIGNALPROCESSBOLT);
		builder.setBolt(Constants.BAISHASIGNALPROCESSBOLT, new BaishaSignalProcessBolt()).fieldsGrouping(Constants.KAFKA_SPOUT_ID, new Fields(Constants.ID));
		LOGGER.info("Setting " + Constants.BAISHASIGNALWEBSOCKETBOLT);
		builder.setBolt(Constants.BAISHASIGNALWEBSOCKETBOLT, new BaishaSignalWebsocketBolt()).shuffleGrouping(Constants.BAISHASIGNALPROCESSBOLT);
		return builder.createTopology();
	}
	
	public StormTopology buildRemoteTopology(){
		TopologyBuilder builder = new TopologyBuilder();
		LOGGER.info("Setting " + Constants.KAFKA_SPOUT_ID);
		builder.setSpout(Constants.KAFKA_SPOUT_ID , new GPSSignalSpout(), spoutsN);
		LOGGER.info("Setting " + Constants.BAISHASIGNALPROCESSBOLT);
		builder.setBolt(Constants.BAISHASIGNALPROCESSBOLT, new BaishaSignalProcessBolt(), processBoltsN).fieldsGrouping(Constants.KAFKA_SPOUT_ID, new Fields(Constants.ID));
		LOGGER.info("Setting " + Constants.BAISHASIGNALWEBSOCKETBOLT);
		builder.setBolt(Constants.BAISHASIGNALWEBSOCKETBOLT, new BaishaSignalWebsocketBolt(), websocketBoltsN).shuffleGrouping(Constants.BAISHASIGNALPROCESSBOLT);
		return builder.createTopology();
	}
	
	public Config buildConfig(){
		Config conf = new Config();
		conf.registerSerialization(RoutePoint.class, FieldSerializer.class);
		conf.registerSerialization(RouteTrack.class, FieldSerializer.class);
		conf.setDebug(true);
		return conf;
	}
	
	public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException, AuthorizationException{
		BaishaSignalTopology topology = new BaishaSignalTopology();
		if(args.length>=2 && args[0].equals("localmode")){
			int runTimeInSeconds = Integer.parseInt(args[1]);
			LOGGER.info("Running topology in local mode in " + runTimeInSeconds + " seconds ...");
			StormRunner.runTopologyLocally(topology.buildLocalTopology(), "testtopologgy", topology.buildConfig(), runTimeInSeconds);
		}
		else{
			LOGGER.info("Running topology in remote mode...");
			StormRunner.runTopologyRemotely(topology.buildRemoteTopology(), Constants.BAISHATOPOLOGYNAME, topology.buildConfig());
		}
	}
	
	
	
	

}
