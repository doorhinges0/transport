package com.realtimestudio.transport.storm.util;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;

public class StormRunner {
	private static final int MILLIS_IN_SEC = 1000;
	
	public static void runTopologyLocally(StormTopology topology, String topologyName, Config conf, int runtimeInSeconds) throws InterruptedException{
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology(topologyName, conf, topology);
		Thread.sleep((long)runtimeInSeconds * MILLIS_IN_SEC);
		cluster.killTopology(topologyName);
		cluster.shutdown();		
	}
	
	public static void runTopologyRemotely(StormTopology topology, String topologyName, Config conf) 
			throws AlreadyAliveException, InvalidTopologyException, AuthorizationException{
		StormSubmitter.submitTopologyWithProgressBar(topologyName, conf, topology);
	}

}