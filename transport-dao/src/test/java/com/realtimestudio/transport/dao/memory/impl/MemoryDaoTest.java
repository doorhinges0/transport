package com.realtimestudio.transport.dao.memory.impl;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.realtimestudio.transport.dao.baisha.CarDao;
import com.realtimestudio.transport.dao.baisha.DriverDao;
import com.realtimestudio.transport.dao.baisha.OrganizationDao;
import com.realtimestudio.transport.dao.baisha.RouteDao;
import com.realtimestudio.transport.dao.config.MemoryDaoConfig;
import com.realtimestudio.transport.model.baisha.Car;
import com.realtimestudio.transport.model.baisha.Driver;
import com.realtimestudio.transport.model.baisha.Organization;
import com.realtimestudio.transport.model.baisha.Route;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MemoryDaoConfig.class)
public class MemoryDaoTest {
	@Autowired
	OrganizationDao organDao;
	
	@Autowired
	DriverDao driverDao;
	
	@Autowired
	CarDao carDao;
	
	@Autowired
	RouteDao routeDao;


	@Test
	public void testOrganDao() {
		List<Organization> allOrgans = organDao.findAll();
		int i = 0;
		for(Organization organ : allOrgans){
			if(i>=2) break;
			System.out.println(organ);
			i++;
		}
		
		for(Organization organ : allOrgans){
			System.out.print(organ.getId()+",");
		}
		System.out.println("");
		
		List<Organization> pagedOrgans = organDao.findAllByPage(2, 10);
		for(Organization organ : pagedOrgans){
			System.out.print(organ.getId()+",");
		}
		System.out.println("");
		
		System.out.println("Total: " + allOrgans.size());
	}
	
	@Test
	public void testDriverDao(){
		List<Driver> allDrivers = driverDao.findAll();
		int i = 0;
		for(Driver driver : allDrivers){
			if(i>=10) break;
			System.out.println(driver);
			i++;
		}
		System.out.println("Total: " + allDrivers.size());
	}
	
	@Test
	public void testCarDao(){
		List<Car >allCars = carDao.findAll();
		int i = 0;
		for(Car car : allCars){
			if(i>=10) break;
			System.out.println(car);
			i++;
		}
		System.out.println("Total: " + allCars.size());	
	}
	
	@Test
	public void testRouteDao(){
		List<Route> allRoutes = routeDao.findAll();
		int i = 0;
		for(Route route : allRoutes){
			if(i>=2) break;
			System.out.println(route);
			i++;
		}
		System.out.println("Total: " + allRoutes.size());
	}

}

