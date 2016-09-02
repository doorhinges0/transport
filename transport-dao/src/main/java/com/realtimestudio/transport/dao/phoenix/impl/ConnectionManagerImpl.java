package com.realtimestudio.transport.dao.phoenix.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.realtimestudio.transport.dao.ConnectionManager;

public class ConnectionManagerImpl implements ConnectionManager<Connection>{
	private final Logger LOGGER = LoggerFactory.getLogger(ConnectionManagerImpl.class);
	
	
	private final BlockingQueue<Connection> pool;
	private final int minConnections;
	private final int maxConnections;
	private final String url;
	private AtomicInteger counter;
	
	//singleton will be enforced in the Spring level
    public ConnectionManagerImpl(int minConnections, int maxConnections, String zkHost){
		this.minConnections = minConnections;
		this.maxConnections = maxConnections;
		this.url = "jdbc:phoenix:"+zkHost+":2181:/hbase-unsecure";
		LOGGER.info("The number of connections will be from "+minConnections + " to "+maxConnections);
		counter = new AtomicInteger(0);
		pool = new ArrayBlockingQueue<>(maxConnections);
		addNewConnections(minConnections);
		LOGGER.info(minConnections + " connections have been created.");
		new ConnMaintenThread().start();
	}

	@Override
	public Connection getConnection() {
		try{
			Connection conn =  pool.take(); //blocking if empty
			return conn;
		}catch(InterruptedException e){
			LOGGER.error("Get connection gets interrupted", e);
			throw new RuntimeException(e);
		}
	}
	
	private Connection createConnection(){
		try {
			return DriverManager.getConnection(url);
		} catch (SQLException e) {
			LOGGER.error("Connection pool creation failed", e);
			throw new RuntimeException(e);
		} 
	}
	
	private void addNewConnections(int num){
		for(int i=0; i<num; i++){
			Connection conn = createConnection();			
			pool.add(conn);
			counter.getAndAdd(num);
		}
	}

	@Override
	public void releaseConnection(Connection connection) {
		if(connection != null){
			try {
				connection.close();
				counter.getAndDecrement();
			} catch (SQLException e) {
				LOGGER.error("Failed to close connection", e);
			}
		}
	}

	@Override
	public void closeAll() {
		for(Connection conn : pool){
			try {
				conn.close();
				counter.getAndDecrement();
			} catch (SQLException e) {
				LOGGER.error("Closing connection failed", e);
			}
		}
		
	}
	
	private class ConnMaintenThread extends Thread{
		
		@Override
		public void run(){
			try{
				while(true){
					sleep(1000*5); 
					//if available connection < min and total connections < max, add new connections					
					if(pool.size()<minConnections && counter.get()<maxConnections){
						addNewConnections(minConnections - pool.size());						
					}
				}
				
			}
			catch(InterruptedException e){
				LOGGER.error("onnMainThread got interrupted");
			}
			
			
		}
		
	}
	

}
