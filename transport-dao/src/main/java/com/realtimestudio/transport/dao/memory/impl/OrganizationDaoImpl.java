package com.realtimestudio.transport.dao.memory.impl;

import static com.realtimestudio.transport.utils.StringUtils.parseLongOrDefault;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.realtimestudio.transport.dao.baisha.OrganizationDao;
import com.realtimestudio.transport.model.baisha.Organization;

public class OrganizationDaoImpl extends CommonDaoImpl<Organization> implements OrganizationDao{

	public OrganizationDaoImpl(){}
	
	public OrganizationDaoImpl(String inputFilePath) {
		super(inputFilePath);
		parse();
	}

	@Override
	protected Organization parseLine(String[] parts) {
		int i = 0;
		Organization organ = new Organization();
		organ.setId(Long.parseLong(parts[i++]));
		organ.setType(parts[i++]);
		organ.setParent(map.get(parseLongOrDefault(parts[i++], -1)));
		organ.setName(parts[i++]);
		return organ;
	}

	@Override
	protected long getId(Organization organ) {
		// TODO Auto-generated method stub
		return organ.getId();
	}

	@Override
	protected void setId(Organization organ, long id) {
		organ.setId(id);		
	}

}
