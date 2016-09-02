package com.realtimestudio.transport.event.simulation.akka.message;

import java.io.Serializable;

public class SimulationSummary implements Serializable{

	private static final long serialVersionUID = 5090915353675955613L;
	
	private String message;
	
	public SimulationSummary(String message) {
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
	
}
