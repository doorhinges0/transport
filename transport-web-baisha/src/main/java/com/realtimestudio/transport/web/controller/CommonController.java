package com.realtimestudio.transport.web.controller;

import java.util.List;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realtimestudio.transport.model.baisha.Organization;
import com.realtimestudio.transport.web.service.CommonService;
import com.realtimestudio.transport.web.service.OrganizationService;

public abstract class CommonController<T, S extends CommonService<T>>{
	private S service;
	
	public CommonController(S service){
		this.service = service;
	}
	
	@RequestMapping(method=RequestMethod.GET) 
	public List<T> getAll(){
		return service.findAll();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public T getById(@PathVariable("id") long id){
		return service.findById(id);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public List<T> getByPage(@RequestParam("page") int pageNum, @RequestParam("itemsPerPage") int itemsPerPage){
		return service.findByPage(pageNum, itemsPerPage);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public T create(@RequestBody T object){
		return service.create(object);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public T update(@PathVariable("id") long id, @RequestBody T object){
		return service.update(id, object);
	}
}
