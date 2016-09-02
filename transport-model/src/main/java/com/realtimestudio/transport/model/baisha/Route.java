package com.realtimestudio.transport.model.baisha;

import java.util.Date;

import com.realtimestudio.transport.utils.JsonUtils;

public class Route {
	private long id;
	private String operationType;
	private Car car;
	private String contractNo;
	private String startPlace;
	private String destProv;
	private String destCity;
	private String destPlace;
	private long planDate;
	private long startTime;
	private long arrivalTime;
	private long finishTime;
	private String productInfo;
	private int productNum;
	private Driver driver1;
	private Driver driver2;
	private int alarmNo;
	
	public Route(){}
	
	public Route(long id){
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getDestProv() {
		return destProv;
	}

	public void setDestProv(String destProv) {
		this.destProv = destProv;
	}

	public String getDestCity() {
		return destCity;
	}

	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}

	public String getDestPlace() {
		return destPlace;
	}

	public void setDestPlace(String destPlace) {
		this.destPlace = destPlace;
	}

	public long getPlanDate() {
		return planDate;
	}

	public void setPlanDate(long planDate) {
		this.planDate = planDate;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	public Driver getDriver1() {
		return driver1;
	}

	public void setDriver1(Driver driver1) {
		this.driver1 = driver1;
	}

	public Driver getDriver2() {
		return driver2;
	}

	public void setDriver2(Driver driver2) {
		this.driver2 = driver2;
	}
	
	public int getAlarmNo(){
		return alarmNo;
	}
	
	public void setAlarmNo(int alarmNo){
		this.alarmNo = alarmNo;
	}
	
	@Override
	public String toString(){
		return JsonUtils.toString(this);
	}
	
}
