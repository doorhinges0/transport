package com.realtimestudio.transport.event.simulation.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.realtimestudio.transport.model.baisha.Car;
import com.realtimestudio.transport.model.baisha.CarEvent;
import com.realtimestudio.transport.model.baisha.Route;
import com.realtimestudio.transport.model.baisha.RoutePoint;

public class InputParser {	
	private static final String SEPERATORESCAPE = "\\|";
	private static final String SEPERATOR = "|";
	
	private static final String EVENTSEPERATOR = ",";
	
	public static RoutePoint parseRoutePoint(String line){
		String parts[] = line.split(SEPERATORESCAPE, -1);
		if(parts.length != 9) throw new RuntimeException("Illegal input: " + line);
		RoutePoint point = new RoutePoint();
		
		point.setRoute(new Route(Long.parseLong(parts[0].trim())));
		point.setCar(new Car(Long.parseLong(parts[1].trim())));
		point.setLocaltion(new com.realtimestudio.transport.model.Location(Float.parseFloat(parts[2].trim()), Float.parseFloat(parts[3].trim())));
		point.setSpeed(Short.parseShort(parts[4].trim()));
		point.setDirection(new com.realtimestudio.transport.model.Direction(Short.parseShort(parts[5].trim())));
		point.setTimestamp(Long.parseLong(parts[7].trim()));
		point.setEvents(parseEvent(parts[8].trim()));
		
		return point;		
	}
	
	public static List<CarEvent> parseEvent(String str){
		if(str.isEmpty()) return new ArrayList<>();
		
		String[] parts = str.split(EVENTSEPERATOR);
		List<CarEvent> carEvents = new ArrayList<>(parts.length);
		for(String part : parts){
			carEvents.add(CarEvent.getCarEvent(part));
		}
		return carEvents;
	}
	
	public static String removeWhiteSpaces(String line){
		String parts[] = line.split(SEPERATORESCAPE, -1);
		if(parts.length != 9) throw new RuntimeException("Illegal input: " + line);
		StringBuilder builder = new StringBuilder(parts[0].trim());
		for(int i=1; i<parts.length; i++){
			builder.append(SEPERATOR);
			builder.append(parts[i].trim());
		}
		return builder.toString();
	}	

}
