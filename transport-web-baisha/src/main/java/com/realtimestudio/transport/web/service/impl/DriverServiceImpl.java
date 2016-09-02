package com.realtimestudio.transport.web.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimestudio.transport.dao.baisha.DriverDao;
import com.realtimestudio.transport.model.baisha.Driver;
import com.realtimestudio.transport.web.service.DriverService;

@Service
public class DriverServiceImpl extends CommonServiceImpl<Driver, DriverDao> implements DriverService {

	@Autowired
	public DriverServiceImpl(DriverDao dao) {
		super(dao);
	}

}
