package com.realtimestudio.transport.event.simulation.akka.message;

import java.io.Serializable;

public class SimulationResult implements Serializable {

	private static final long serialVersionUID = -6175238747664794333L;
	
	private String filePath;
	private String carid;
	private String routeid;
	private long totalPoints;
	private boolean hasSucceeded;
	private String errorMessage;
	
	public SimulationResult(String filePath, String carid, String routeid, long totalPoints, boolean hasSucceeded, String errorMessage) {
		super();
		this.filePath = filePath;
		this.carid = carid;
		this.routeid = routeid;
		this.hasSucceeded = hasSucceeded;
		this.errorMessage = errorMessage;
		this.totalPoints = totalPoints;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean isHasSucceeded() {
		return hasSucceeded;
	}

	public void setHasSucceeded(boolean hasSucceeded) {
		this.hasSucceeded = hasSucceeded;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getCarid() {
		return carid;
	}

	public void setCarid(String carid) {
		this.carid = carid;
	}

	public String getRouteid() {
		return routeid;
	}

	public void setRouteid(String routeid) {
		this.routeid = routeid;
	}

	public long getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(long totalPoints) {
		this.totalPoints = totalPoints;
	}

	
	

}
