package com.realtimestudio.transport.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realtimestudio.transport.model.baisha.Driver;
import com.realtimestudio.transport.web.service.DriverService;

@RestController
@RequestMapping("/drivers")
public class DriverController extends CommonController<Driver, DriverService> {

	@Autowired
	public DriverController(DriverService service) {
		super(service);
	}

}
