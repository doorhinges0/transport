package com.realtimestudio.transport.event.simulation.akka.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.realtimestudio.transport.event.simulation.akka.message.SimulationSummary;

import akka.actor.UntypedActor;

public class SimulationListener extends UntypedActor{
	private static final Logger LOGGER = LoggerFactory.getLogger(SimulationListener.class);

	@Override
	public void onReceive(Object message) {
		if(message instanceof SimulationSummary){
			SimulationSummary summaryMessage = (SimulationSummary) message;
			LOGGER.info(summaryMessage.getMessage());
			getContext().system().shutdown();;
		}
		else{
			unhandled(message);
		}
		
	}

}
