package com.realtimestudio.transport.event;

import com.realtimestudio.transport.model.RoutePoint;

public interface GPSSignalParser {
	String getID();
	RoutePoint getRoutePoint();

}
