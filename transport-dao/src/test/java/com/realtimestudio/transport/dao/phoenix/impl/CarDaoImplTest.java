package com.realtimestudio.transport.dao.phoenix.impl;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.realtimestudio.transport.dao.ConnectionManager;
import com.realtimestudio.transport.dao.baisha.CarDao;
import com.realtimestudio.transport.model.baisha.Car;

public class CarDaoImplTest {
	ConnectionManager<Connection> connectionManager = null;
	CarDao dao = null;
	@Before
	public void setup() throws ClassNotFoundException{
		connectionManager = new ConnectionManagerImpl(1, 2, "localhost");
		dao = new CarDaoImpl(connectionManager);
	}

	@Test
	public void findAllTest() {
		List<Car> allCars = dao.findAll();
		System.out.println("Starting..");
		for(Car car : allCars){
			System.out.println(car.getId());
			System.out.println(car.getCode());
		}
		System.out.println("Ending...");
		
	}
	
	@Test
	public void findByIdTest(){
		Car car = dao.findById(856L);
		assertTrue(car.getId()==856);
	}

}
