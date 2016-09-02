package com.realtimestudio.transport.web.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimestudio.transport.dao.baisha.CarDao;
import com.realtimestudio.transport.model.baisha.Car;
import com.realtimestudio.transport.web.service.CarService;

@Service
public class CarServiceImpl extends CommonServiceImpl<Car, CarDao> implements CarService {

	@Autowired
	public CarServiceImpl(CarDao dao) {
		super(dao);
	}

}
