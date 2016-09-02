package com.realtimestudio.transport.storm.bolt;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.realtimestudio.transport.dao.baisha.RouteDao;
import com.realtimestudio.transport.model.baisha.Car;
import com.realtimestudio.transport.model.baisha.Driver;
import com.realtimestudio.transport.model.baisha.Route;
import com.realtimestudio.transport.model.baisha.RoutePoint;
import com.realtimestudio.transport.model.baisha.RouteTrack;
import com.realtimestudio.transport.storm.util.Constants;
import com.realtimestudio.transport.utils.SpringUtils;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class BaishaSignalProcessBolt extends BaseBasicBolt {
	private static final long serialVersionUID = 3391285196942314063L;
	private final static Logger LOGGER = LoggerFactory.getLogger(BaishaSignalProcessBolt.class);
	
	private RouteDao routeDao;
	private Map<Long, Route> cache;
	
	@Override
	public void prepare(Map conf, TopologyContext context){
		LOGGER.info("BashaSignalProcessBolt is being initialized");
		routeDao = SpringUtils.getBean(RouteDao.class);
		cache = new HashMap<>();
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String key = (String) input.getValueByField(Constants.ID);
		RoutePoint point = (RoutePoint) input.getValueByField(Constants.ROUTEPOINTFIELD);
		
		RouteTrack track = constructTuple(point);
		if(track != null){
			collector.emit(new Values(key, track));
		}		
	}
	
	private RouteTrack constructTuple(RoutePoint point){
		Route route = getRoute(point.getRoute().getId());
				
		Car car = route.getCar();
		long carID = car == null ? 0 : car.getId();
		String carLicense = car == null ? "unknown" : car.getCode();
		
		Driver driver = route.getDriver1();
		long driverID = driver == null ? 0 : driver.getId();
		String driverName = driver == null ? "unknown" : driver.getName();
		
		return new RouteTrack(route.getId(), route.getDestPlace(), carID, carLicense, driverID, driverName
				, point.getLocation().getLongitude(), point.getLocation().getLatitude(), point.getSpeed(), point.getTimestamp(), point.getEvents());
	}
	
	private Route getRoute(long routeId){
		if(cache.containsKey(routeId)){
			return cache.get(routeId);
		}
		else{
			Route route = routeDao.findById(routeId);
			if(route==null){
				LOGGER.error("Can not find the route " + routeId);
				return null;
			}
			else{
				cache.put(routeId, route);
				return route;
			}
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare( new Fields(Constants.ID,  Constants.TRACKPOINTFIELD));		
	}
}
