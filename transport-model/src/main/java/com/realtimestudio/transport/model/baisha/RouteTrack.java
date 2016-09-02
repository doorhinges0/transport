package com.realtimestudio.transport.model.baisha;

import java.io.Serializable;
import java.util.List;

import com.realtimestudio.transport.utils.JsonUtils;

public class RouteTrack implements Serializable{
	private static final long serialVersionUID = -1923764952564688554L;
	
	private long routeId;
	private String destPlace;
	private long carId;
	private String carLicense;
	private long driverId;
	private String driverName;
	private float longitude;
	private float latitude;
	private short speed;
	private long timestamp;
	private List<CarEvent> events;
	
	public RouteTrack(long routeId, String destPlace, long carId, String carLicense, long driverId, String driverName,
			float longitude, float latitude, short speed, long timestamp, List<CarEvent> events) {
		super();
		this.routeId = routeId;
		this.destPlace = destPlace;
		this.carId = carId;
		this.carLicense = carLicense;
		this.driverId = driverId;
		this.driverName = driverName;
		this.longitude = longitude;
		this.latitude = latitude;
		this.speed = speed;
		this.timestamp = timestamp;
		this.events = events;
	}
	
	@Override
	public String toString(){
		return JsonUtils.toString(this);
	}
	
	

}
