package com.realtimestudio.transport.web.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimestudio.transport.dao.baisha.CarDao;
import com.realtimestudio.transport.dao.baisha.RouteDao;
import com.realtimestudio.transport.model.baisha.Car;
import com.realtimestudio.transport.model.baisha.Route;
import com.realtimestudio.transport.web.service.CarService;
import com.realtimestudio.transport.web.service.RouteService;

@Service
public class RouteServiceImpl extends CommonServiceImpl<Route, RouteDao> implements RouteService {

	@Autowired
	public RouteServiceImpl(RouteDao dao) {
		super(dao);
	}

}
