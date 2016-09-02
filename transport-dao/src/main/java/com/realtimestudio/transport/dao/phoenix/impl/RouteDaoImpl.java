package com.realtimestudio.transport.dao.phoenix.impl;

import static com.realtimestudio.transport.dao.baisha.Constants.ROUTEDBFIELDS;
import static com.realtimestudio.transport.dao.baisha.Constants.ROUTEDBNAME;
import static com.realtimestudio.transport.dao.baisha.Constants.ROUTEENTITYFIELDS;
import static com.realtimestudio.transport.dao.baisha.Constants.generateDeleteQuery;
import static com.realtimestudio.transport.dao.baisha.Constants.generateFindAllQuery;
import static com.realtimestudio.transport.dao.baisha.Constants.generateFindByIdQuery;
import static com.realtimestudio.transport.dao.baisha.Constants.generatePutQuery;
import static com.realtimestudio.transport.dao.baisha.Constants.getEntity2DBFieldsMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.realtimestudio.transport.dao.ConnectionManager;
import com.realtimestudio.transport.dao.baisha.RouteDao;
import com.realtimestudio.transport.model.baisha.Car;
import com.realtimestudio.transport.model.baisha.Driver;
import com.realtimestudio.transport.model.baisha.Route;

public class RouteDaoImpl extends CommonDaoImpl<Route> implements RouteDao{
	private static Map<String, String> fieldsMap = getEntity2DBFieldsMap(ROUTEENTITYFIELDS, ROUTEDBFIELDS);

	public RouteDaoImpl(ConnectionManager<Connection> connectionManager) {
		super(connectionManager
				, generateFindAllQuery(ROUTEDBFIELDS, ROUTEDBNAME)
				, generateFindByIdQuery(ROUTEDBFIELDS, ROUTEDBNAME)
				, generatePutQuery(ROUTEDBFIELDS, ROUTEDBNAME)
				, generateDeleteQuery(ROUTEDBNAME));
	}
	
	@Override
	protected Route parseResult(ResultSet rs) throws SQLException {
		Route route = new Route();
		route.setId(rs.getLong(fieldsMap.get("id")));
		route.setOperationType(rs.getString(fieldsMap.get("operationType")));
		route.setCar(new Car(rs.getLong(fieldsMap.get("car"))));
		route.setPlanDate(rs.getLong(fieldsMap.get("planDate")));
		route.setContractNo(rs.getString(fieldsMap.get("contractNo")));
		route.setStartPlace(rs.getString(fieldsMap.get("startPlace")));
		route.setDestProv(rs.getString(fieldsMap.get("destProv")));
		route.setDestCity(rs.getString(fieldsMap.get("destCity")));
		route.setDestPlace(rs.getString(fieldsMap.get("destPlace")));
		route.setProductNum(rs.getInt(fieldsMap.get("productNum")));
		route.setProductInfo(rs.getString(fieldsMap.get("productInfo")));
		route.setDriver1(new Driver(rs.getLong(fieldsMap.get("driver1"))));
		route.setDriver2(new Driver(rs.getLong(fieldsMap.get("driver2"))));
		route.setStartTime(rs.getLong(fieldsMap.get("startTime")));
		route.setArrivalTime(rs.getLong(fieldsMap.get("arrivalTime")));
		route.setFinishTime(rs.getLong(fieldsMap.get("finishTime")));
		route.setAlarmNo(rs.getInt(fieldsMap.get("alarmNo")));
		return route;
	}

	@Override
	protected void enrichPreparedStatement(PreparedStatement stmt, Route route) throws SQLException {
		int paramIndex = 1;
		stmt.setLong(paramIndex++, route.getId());
		stmt.setString(paramIndex++, route.getOperationType());
		stmt.setLong(paramIndex++, route.getCar().getId());
		stmt.setLong(paramIndex++, route.getPlanDate());
		stmt.setString(paramIndex++, route.getContractNo());
		stmt.setString(paramIndex++, route.getStartPlace());
		stmt.setString(paramIndex++, route.getDestProv());
		stmt.setString(paramIndex++, route.getDestCity());
		stmt.setString(paramIndex++, route.getDestPlace());
		stmt.setInt(paramIndex++, route.getProductNum());
		stmt.setString(paramIndex++, route.getProductInfo());
		stmt.setLong(paramIndex++, route.getDriver1().getId());
		stmt.setLong(paramIndex++, route.getDriver2().getId());
		stmt.setLong(paramIndex++, route.getStartTime());
		stmt.setLong(paramIndex++, route.getArrivalTime());
		stmt.setLong(paramIndex++, route.getFinishTime());
		stmt.setInt(paramIndex++, route.getAlarmNo());
		
	}

}
