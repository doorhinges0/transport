package com.realtimestudio.transport.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realtimestudio.transport.model.baisha.Organization;
import com.realtimestudio.transport.web.service.OrganizationService;

@RestController
@RequestMapping("/organizations")
public class OrganizationController extends CommonController<Organization, OrganizationService>{

	@Autowired
	public OrganizationController(OrganizationService service) {
		super(service);
	}
}
