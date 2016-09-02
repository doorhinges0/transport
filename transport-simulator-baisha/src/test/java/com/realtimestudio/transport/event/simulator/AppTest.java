package com.realtimestudio.transport.event.simulator;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.realtimestudio.transport.event.simulation.App;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=App.class)
public class AppTest {

	@Test
	public void stdoutTest() throws InterruptedException {
		String[] args = {"stdoutSignalEmitter"};
		App.main(args);
		TimeUnit.SECONDS.sleep(5);
	}
	
	@Test
	public void kafkaTest() throws InterruptedException {
		String[] args = {"kafkaSignalEmitter"};
		App.main(args);
		TimeUnit.SECONDS.sleep(1);
	}
	
	@Test
	public void httpTest() throws InterruptedException {
		String[] args = {"httpSignalEmitter"};
		App.main(args);
		TimeUnit.SECONDS.sleep(5);
	}

}
