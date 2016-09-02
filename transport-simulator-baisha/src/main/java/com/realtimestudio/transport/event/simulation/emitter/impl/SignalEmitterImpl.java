package com.realtimestudio.transport.event.simulation.emitter.impl;

import com.realtimestudio.transport.event.simulation.akka.actor.SimulationListener;
import com.realtimestudio.transport.event.simulation.akka.actor.SimulationMaster;
import com.realtimestudio.transport.event.simulation.akka.message.FilePathMessage;
import com.realtimestudio.transport.event.simulation.emitter.SignalEmitter;
import com.realtimestudio.transport.streaming.SignalCollector;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class SignalEmitterImpl implements SignalEmitter{
	//private static final String fileDirectory = "/users/yejiawei/Documents/datapot/20160719/routes";
	
	private String fileDirectory;
	private SignalCollector signalCollector;
	private long interval;
	
	public SignalEmitterImpl(String fileDirectory, SignalCollector signalCollector, long interval){
		this.fileDirectory = fileDirectory;
		this.signalCollector = signalCollector;
		this.interval = interval;
	}

	@Override
	public void emit() {
		ActorSystem actorSystem = ActorSystem.create("SignalEmitter");
		ActorRef simulationListener = actorSystem.actorOf(Props.create(SimulationListener.class), "simulationListener");
		ActorRef simulationMaster = actorSystem.actorOf(Props.create(SimulationMaster.class, simulationListener, signalCollector, interval), "simulationMaster");
		simulationMaster.tell(new FilePathMessage(fileDirectory), null);
		
	}
/*	
	public static void main (String[] args) throws InterruptedException {
		SignalCollector signalCollector = new SignalCollector(){

			@Override
			public void send(String signal) {
				System.out.println(signal);
				
			}

			@Override
			public void send(String key, String signal) {
				System.out.println("key=" + key + "; signal=" + signal);
				
			}

			@Override
			public void close() {
				System.out.println("collector is closed");
				
			}
			
		};
		
		SignalEmitter emitter = new SignalEmitterImpl("/users/yejiawei/Documents/datapot/20160719/routes", signalCollector, 10);
		emitter.emit();
		Thread.sleep(100);
	}
	*/


}
