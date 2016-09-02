package com.realtimestudio.transport.event.simulation.akka.message;

import java.io.Serializable;

public class FilePathMessage implements Serializable{

	private static final long serialVersionUID = 1628359523371959345L;
	
	private String filePath;
	
	public FilePathMessage(String filePath){
		this.filePath = filePath;
	}
	
	public String getFilePath(){
		return filePath;
	}

}
