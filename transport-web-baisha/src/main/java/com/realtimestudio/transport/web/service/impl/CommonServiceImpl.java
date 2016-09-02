package com.realtimestudio.transport.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realtimestudio.transport.dao.baisha.CommonDao;
import com.realtimestudio.transport.dao.baisha.OrganizationDao;
import com.realtimestudio.transport.model.baisha.Organization;
import com.realtimestudio.transport.web.service.CommonService;
import com.realtimestudio.transport.web.service.OrganizationService;

public abstract class CommonServiceImpl<T, DAO extends CommonDao<Long, T>> implements CommonService<T>{
	private DAO dao;
	
	public CommonServiceImpl(DAO dao){
		this.dao = dao;
	}

	@Override
	public List<T> findAll() {
		return dao.findAll();
	}

	@Override
	public T findById(long id) {
		return dao.findById(id);
	}

	@Override
	public List<T> findByPage(int pageNum, int itemsPerPage) {
		return dao.findAllByPage(pageNum, itemsPerPage);
	}

	@Override
	public T create(T object) {
		return dao.create(object);
		
	}

	@Override
	public T update(long id, T object) {
		return dao.put(id, object);
		
	}

}
