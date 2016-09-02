package com.realtimestudio.transport.event.simulation.akka.actor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.realtimestudio.transport.event.simulation.akka.message.FilePathMessage;
import com.realtimestudio.transport.event.simulation.akka.message.SimulationResult;
import com.realtimestudio.transport.event.simulation.utils.InputParser;
import com.realtimestudio.transport.streaming.SignalCollector;

import akka.actor.UntypedActor;

public class SimulationWorker extends UntypedActor{
	private static final Logger LOGGER = LoggerFactory.getLogger(SimulationWorker.class);
	
	private SignalCollector signalCollector;
	private long interval;
	
	public SimulationWorker(SignalCollector signalCollector, long interval){
		this.signalCollector = signalCollector;
		this.interval = interval;
	}

	@Override
	public void onReceive(Object message) {
		if(message instanceof FilePathMessage){
			FilePathMessage filePathMessage = (FilePathMessage) message;
			File file = new File(filePathMessage.getFilePath());
			if(!file.isFile()){
				String errorMessage = filePathMessage.getFilePath() + " does not exit or is not a file";
				LOGGER.error(errorMessage);
				SimulationResult result = new SimulationResult(filePathMessage.getFilePath(), null, null, 0, false, errorMessage);
				getSender().tell(result, getSelf());
				return;
			}
			
			String[] parts = file.getName().split("\\.")[0].split("_");
			String carid = parts[0];
			String routeid = parts[1];
			LOGGER.info(String.format("Simualation is started: car=%s, route=%s", carid, routeid));
			
			long totalPoints = 0;
			String line = null;
			LineIterator iterator = null;
			try{
				iterator = FileUtils.lineIterator(file, "UTF-8");
				while(iterator.hasNext()){
					line = iterator.nextLine();
					line = InputParser.removeWhiteSpaces(line);
					signalCollector.send(routeid, line);
					totalPoints ++;
					
					TimeUnit.MILLISECONDS.sleep(interval);
				}
				
			}
			catch(Exception e){
				String errorMessage = "worker failed at line: "+ line + " " +e.getMessage();
				LOGGER.error(errorMessage);
				SimulationResult result = new SimulationResult(file.getAbsolutePath(), carid, routeid, totalPoints, false, errorMessage);
				getSender().tell(result, getSelf());
				return;
			}
			finally{
				LineIterator.closeQuietly(iterator);
			}
			
			SimulationResult result = new SimulationResult(file.getAbsolutePath(), carid, routeid, totalPoints, true, null);
			getSender().tell(result,  getSelf());		
			
		}
		else{
			unhandled(message);
		}
		
	}
	

}
