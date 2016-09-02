package com.realtimestudio.transport.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realtimestudio.transport.model.baisha.Car;
import com.realtimestudio.transport.web.service.CarService;

@RestController
@RequestMapping("/cars")
public class CarController extends CommonController<Car, CarService>{

	@Autowired
	public CarController(CarService service) {
		super(service);
	}
	
}
