package com.realtimestudio.transport.dao.memory.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.realtimestudio.transport.dao.baisha.CarDao;
import com.realtimestudio.transport.dao.baisha.DriverDao;
import com.realtimestudio.transport.dao.baisha.OrganizationDao;
import com.realtimestudio.transport.model.baisha.Car;

import static com.realtimestudio.transport.utils.StringUtils.*;

public class CarDaoImpl extends CommonDaoImpl<Car> implements CarDao{
	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired
	private DriverDao driverDao;

	public CarDaoImpl(String inputFilePath, OrganizationDao organDao, DriverDao driverDao) {
		super(inputFilePath);
		this.organizationDao = organDao;
		this.driverDao = driverDao;
		parse();
	}

	@Override
	protected Car parseLine(String[] parts) {
		Car car = new Car();
		int i = 0;
		car.setId(Long.parseLong(parts[i++]));
		car.setCode(parts[i++]);
		car.setCarType(parts[i++]);
		car.setStatus(parts[i++]);
		car.setOrgan(organizationDao.findById(parseLongOrDefault(parts[i++], -1)));
		i++; //skip device
		car.setCamNum(parseIntOrDefault(parts[i++], -1));
		i++; //skip contact
		i++; //skip code_type
		car.setRegisterTime(parseLongOrDefault(parts[i++], 0L));
		car.setWeight(parseDoubleOrDefault(parts[i++], 0));
		car.setHeight(parseDoubleOrDefault(parts[i++], 0));
		car.setLength(parseDoubleOrDefault(parts[i++], 0));
		i++; //skip econd_contact
		i++; //skip code_color
		car.setPurchaseDate(parseLongOrDefault(parts[i++], 0L));
		car.setDriver2(driverDao.findById(parseLongOrDefault(parts[i++], -1)));
		car.setDriver1(driverDao.findById(parseLongOrDefault(parts[i++], -1)));
		return car;

	}

	@Override
	protected long getId(Car car) {
		return car.getId();
	}

	@Override
	protected void setId(Car car, long id) {
		car.setId(id);		
	}

}
