package com.realtimestudio.transport.storm.util;

public class Constants {
	//storm fields names
	public static final String ID = "ID";
	public static final String CARIDFIELD = "CARID";
	public static final String ROUTEPOINTFIELD = "ROUTEPOINT";
	public static final String TRACKPOINTFIELD = "TRACKPOINT";
	
	public static final String LANECROSSING = "lane_crossing";
	
	//topology, spout, bolt ids
	public static final String KAFKA_SPOUT_ID = "kafkaSpout";
	public static final String LOG_CARSIGNAL_BOLT_ID = "logCarSignalBolt";
	public static final String HDFS_CARSIGNAL_BOLT_ID = "hdfsCarSignalBolt";
	public static final String HBASE_CARSIGNAL_BOLT_ID = "hbaseCarSignalBolt";
	public static final String TOPOLOGYNAME = "GPSSignalTopology";
	public static final String BAISHASIGNALPROCESSBOLT = "baishaSignalProcessBolt";
	public static final String BAISHASIGNALWEBSOCKETBOLT="baishaSignalWebsocketBolt";
	public static final String BAISHATOPOLOGYNAME = "BSGPSSignalTopology";
	
	//storm config
	public static final String TOPOLOGYWORKERS = "topology.workers";
	public static final String TOPOLOGYSPOUTS = "topology.spouts";
	public static final String TOPOLOGYHBASEBOLTS = "topology.hbasebolts";
	public static final String TOPOLOGYHDFSBOLTS = "topology.hdfsbolts";
	public static final String TOPOLOGYBSSIGNALPROCESSBOLT = "topology.baisha.signalprocessbolt";
	public static final String TOPOLOGYBSSIGNALWEBSOCKETBOLT ="topology.baisha.signalwebsocketbolt";
	
	//property names
	//kafka spout
	public static final String KAFKAHOST = "kafka.zookeeper.host.port";
	public static final String KAFKATOPIC = "kafka.topic";
	public static final String KAFKAZKROOT = "kafka.zkRoot";
	public static final String KAFKACONSUMERGROUPID = "kafka.consumergroupid";
	public static final String SPOUTTHREADS = "spout.thread.count";
	
	//HDFS
	public static final String HDFSPATH = "hdfs.path";
	public static final String HDFSURL = "hdfs.url";
	public static final String HDFSFILEPREFIX = "hdfs.file.prefix";
	public static final String HDFSFILEROTATIONTIMEMIN = "hdfs.file.rotation.time.minutes";
	
	//HBASE
	public static final String HBASEPERSISTALL = "hbase.persist.all.events";

    //HIVE
	public static final String HIVEMETASTOREURL = "hive.metastore.url";
	public static final String HIVESTAGINGTABLE = "hive.staging.table.name";
	public static final String HIVEDBNAME = "hive.database.name";
	
	//activemq
	public static final String NOTIFICATIONTOPICUSER = "notification.topic.user";
	public static final String NOTIFICATIONTOPICPASSWORD = "notification.topic.password";
	public static final String NOTIFICATIONTOPICCONNECTIONURL = "notification.topic.connection.url";
	public static final String NOTIFICATIONTOPICALLSIGNAL = "notification.topic.all_signal";
	

	

}
