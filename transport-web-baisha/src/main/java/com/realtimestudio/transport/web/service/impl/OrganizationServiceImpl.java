package com.realtimestudio.transport.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimestudio.transport.dao.baisha.OrganizationDao;
import com.realtimestudio.transport.model.baisha.Organization;
import com.realtimestudio.transport.web.service.OrganizationService;

@Service
public class OrganizationServiceImpl extends CommonServiceImpl<Organization, OrganizationDao> implements OrganizationService{

	@Autowired
	public OrganizationServiceImpl(OrganizationDao dao) {
		super(dao);
	}

}
