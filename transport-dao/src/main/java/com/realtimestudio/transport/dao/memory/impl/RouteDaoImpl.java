package com.realtimestudio.transport.dao.memory.impl;

import static com.realtimestudio.transport.utils.StringUtils.*;

import java.text.ParseException;


import com.realtimestudio.transport.dao.baisha.CarDao;
import com.realtimestudio.transport.dao.baisha.DriverDao;
import com.realtimestudio.transport.dao.baisha.RouteDao;
import com.realtimestudio.transport.model.baisha.Route;

public class RouteDaoImpl extends CommonDaoImpl<Route> implements RouteDao{
	private DriverDao driverDao;
	private CarDao carDao;

	public RouteDaoImpl(String inputFilePath, DriverDao driverDao, CarDao carDao) {
		super(inputFilePath);
		this.driverDao = driverDao;
		this.carDao = carDao;
		parse();
	}

	@Override
	protected Route parseLine(String[] parts) throws ParseException {
		Route route = new Route();
		int i = 0;
		route.setId(Long.parseLong(parts[i++]));
		i++; //skip hy_attemper_no
		route.setOperationType(parts[i++]);
		route.setCar(carDao.findById(parseLongOrDefault(parts[i++], -1)));
		route.setPlanDate(parseLongOrDefault(parts[i++], 0));
		route.setContractNo(parts[i++]);
		route.setStartPlace(parts[i++]);
		route.setDestProv(parts[i++]);
		route.setDestCity(parts[i++]);
		route.setDestPlace(parts[i++]);
		route.setProductNum(parseIntOrDefault(parts[i++], 0));
		route.setProductInfo(parts[i++]);
		i++; //skip hy_pc_no
		route.setDriver1(driverDao.findById(parseLongOrDefault(parts[i++], -1)));
		route.setDriver2(driverDao.findById(parseLongOrDefault(parts[i++], -1)));
		i++; //skip tran_ok_status
		route.setStartTime(parseLongOrDefault(parts[i++], 0));
		i++; //skip arrive_ok_status
		route.setArrivalTime(parseLongOrDefault(parts[i++], 0));
		i++; //skip finish_ok_status
		route.setFinishTime(parseLongOrDefault(parts[i++], 0));
		return route;		
	}

	@Override
	protected long getId(Route route) {
		return route.getId();
	}

	@Override
	protected void setId(Route route, long id) {
		route.setId(id);		
	}

}
