package com.realtimestudio.transport.event.simulator;

import org.junit.Test;

import com.realtimestudio.transport.streaming.impl.KafkaSignalCollector;

public class KafkaTest {

	@Test
	public void test() {
		KafkaSignalCollector client = new KafkaSignalCollector("10.10.81.168:9092", "test1");
		client.send("This is a test");
		client.close();
	}

}
