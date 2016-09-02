package com.realtimestudio.transport.model.baisha;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.realtimestudio.transport.model.Direction;
import com.realtimestudio.transport.model.Location;

public class RoutePoint implements Serializable{
	private static final long serialVersionUID = 7626530149875355828L;
	private Route route;
	private Car car;
	private Location location;
	private short speed;
	private long timestamp;
	private Direction direction;
	private List<CarEvent> events;
	
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	public Car getCar() {
		return car;
	}
	public RoutePoint setCar(Car car) {
		this.car = car;
		return this;
	}
	public Location getLocation() {
		return location;
	}
	public RoutePoint setLocaltion(Location localtion) {
		this.location = localtion;
		return this;
	}
	public short getSpeed() {
		return speed;
	}
	public RoutePoint setSpeed(short speed) {
		this.speed = speed;
		return this;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public RoutePoint setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		return this;
	}
	public Direction getDirection() {
		return direction;
	}
	public RoutePoint setDirection(Direction direction) {
		this.direction = direction;
		return this;
	}
	public List<CarEvent> getEvents() {
		return events;
	}
	public RoutePoint setEvents(List<CarEvent> events) {
		this.events = events;
		return this;
	}	
	
	@Override
	public String toString(){
		return String.format("Route: route=%s, car=%s, location=%s,speed=%d, timestamp=%s, direction=%s, events=%s"
				,route.toString(), car.toString(), location.toString(), speed, timestamp, direction.toString(), events.toString());
	}
	
	

}
