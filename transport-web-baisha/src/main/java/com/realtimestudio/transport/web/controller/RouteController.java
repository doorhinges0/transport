package com.realtimestudio.transport.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realtimestudio.transport.model.baisha.Route;
import com.realtimestudio.transport.web.service.RouteService;

@RestController
@RequestMapping("/routes")
public class RouteController extends CommonController<Route, RouteService> {

	@Autowired
	public RouteController(RouteService service) {
		super(service);
	}

}
