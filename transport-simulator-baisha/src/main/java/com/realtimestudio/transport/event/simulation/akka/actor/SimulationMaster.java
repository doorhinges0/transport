package com.realtimestudio.transport.event.simulation.akka.actor;

import java.io.File;
import java.io.FileFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.realtimestudio.transport.event.simulation.akka.message.FilePathMessage;
import com.realtimestudio.transport.event.simulation.akka.message.SimulationResult;
import com.realtimestudio.transport.event.simulation.akka.message.SimulationSummary;
import com.realtimestudio.transport.streaming.SignalCollector;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;

public class SimulationMaster extends UntypedActor{
	private static final Logger LOGGER = LoggerFactory.getLogger(SimulationMaster.class);
			
	private ActorRef listener;
	private boolean started = false;
	private int numberOfWorkers;
	private int numberOfSuccess = 0;
	private int numberOfFailed = 0;
	private SignalCollector signalCollector;
	private long interval;
	
	public SimulationMaster(ActorRef listener, SignalCollector signalCollector, long interval){
		this.listener = listener;
		this.signalCollector = signalCollector;
		this.interval = interval;
	}

	@Override
	public void onReceive(Object message) {
		if(message instanceof FilePathMessage){
			if(started){
				LOGGER.error("Simulation Master already started");
				return;
			}
			started = true;
			FilePathMessage filePathMessage = (FilePathMessage) message;
			File directory = new File(filePathMessage.getFilePath());
			if(!directory.isDirectory()){				
				String errorMessage = "The path does not exist or is not directory: " + filePathMessage.getFilePath();
				LOGGER.error(errorMessage);
				listener.tell(new SimulationSummary(errorMessage), null);
				getContext().stop(getSelf());
			}
			File[] files = directory.listFiles(new FileFilter(){

				@Override
				public boolean accept(File file) {
					return file.getName().endsWith("csv");
				}
				
			});
			numberOfWorkers = files.length;		
			
			ActorRef workerRouter = getContext().actorOf(Props.create(SimulationWorker.class, signalCollector, interval).withRouter(new RoundRobinPool(numberOfWorkers)), "workerRouter");
			for(int i=0; i<numberOfWorkers; i++){
				workerRouter.tell(new FilePathMessage(files[i].getAbsolutePath()), getSelf());
			}
			
		}
		else if(message instanceof SimulationResult){ 
			SimulationResult  result = (SimulationResult) message;
			if(result.isHasSucceeded()){
				numberOfSuccess++;
				LOGGER.info(result.getFilePath() + " is completed");
			}
			else{
				numberOfFailed++;
				LOGGER.error(result.getFilePath() + " failed: " + result.getErrorMessage());
			}
			
			if(numberOfSuccess+numberOfFailed == numberOfWorkers){
				String summaryMessage = " The simulation is completed. successes="+numberOfSuccess+"; failed="+numberOfFailed;
				listener.tell(new SimulationSummary(summaryMessage), getSelf());
				getContext().stop(getSelf());
			}
			
		}
		
	}

}
