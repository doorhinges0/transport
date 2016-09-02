package com.realtimestudio.transport.model.baisha.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.realtimestudio.transport.model.baisha.RoutePoint;
import com.realtimestudio.transport.model.baisha.Car;
import com.realtimestudio.transport.model.baisha.CarEvent;
import com.realtimestudio.transport.model.baisha.Route;

public class GPSSignalParserImpl implements GPSSignalParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(GPSSignalParserImpl.class);
	
	private static final String SEPERATORESCAPE = "\\|";
	private static final String EVENTSEPERATOR = ",";
	
	private RoutePoint point;
	
	public GPSSignalParserImpl(String signal){
		point = new RoutePoint();
		parse(signal);
		
	}
	
	private void parse(String signal) {
		String parts[] = signal.split(SEPERATORESCAPE, -1);
		if (parts.length != 9)
			throw new RuntimeException("Illegal signal input: " + signal);
		point.setRoute(new Route(Long.parseLong(parts[0].trim())));
		point.setCar(new Car(Long.parseLong(parts[1].trim())));
		point.setLocaltion(new com.realtimestudio.transport.model.Location(Float.parseFloat(parts[2].trim()),
				Float.parseFloat(parts[3].trim())));
		point.setSpeed(Short.parseShort(parts[4].trim()));
		point.setDirection(new com.realtimestudio.transport.model.Direction(Short.parseShort(parts[5].trim())));
		point.setTimestamp(Long.parseLong(parts[7].trim()));
		point.setEvents(parseEvent(parts[8].trim()));
		

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

	@Override
	public String getID() {
		return String.valueOf(point.getRoute().getId());
	}

	@Override
	public RoutePoint getRoutePoint() {
		return point;
	}

}
