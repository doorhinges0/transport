package com.realtimestudio.transport.model.baisha;

import com.realtimestudio.transport.utils.JsonUtils;

public class Organization {
	private long id;
	private String type;
	private Organization parent;
	private String name;
	
	public Organization(){}
	
	public Organization(long id){
		this.id = id;
	}
	public Organization(long id, String type, Organization parent, String name) {
		super();
		this.id = id;
		this.type = type;
		this.parent = parent;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Organization getParent() {
		return parent;
	}
	public void setParent(Organization parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return JsonUtils.toString(this);
	}
	

}
