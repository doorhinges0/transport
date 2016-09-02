package com.realtimestudio.transport.dao.phoenix.impl;

import static com.realtimestudio.transport.dao.baisha.Constants.CARDBFIELDS;
import static com.realtimestudio.transport.dao.baisha.Constants.CARDBNAME;
import static com.realtimestudio.transport.dao.baisha.Constants.CARENTITYFIELDS;
import static com.realtimestudio.transport.dao.baisha.Constants.generateDeleteQuery;
import static com.realtimestudio.transport.dao.baisha.Constants.generateFindAllQuery;
import static com.realtimestudio.transport.dao.baisha.Constants.generateFindByIdQuery;
import static com.realtimestudio.transport.dao.baisha.Constants.generatePutQuery;
import static com.realtimestudio.transport.dao.baisha.Constants.getEntity2DBFieldsMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.realtimestudio.transport.dao.ConnectionManager;
import com.realtimestudio.transport.dao.baisha.CarDao;
import com.realtimestudio.transport.model.baisha.Car;
import com.realtimestudio.transport.model.baisha.Driver;
import com.realtimestudio.transport.model.baisha.Organization;

public class CarDaoImpl extends CommonDaoImpl<Car> implements CarDao{
	private static Map<String, String> fieldsMap = getEntity2DBFieldsMap(CARENTITYFIELDS, CARDBFIELDS);

	public CarDaoImpl(ConnectionManager<Connection> connectionManager) {
		super(connectionManager
				, generateFindAllQuery(CARDBFIELDS, CARDBNAME)
				, generateFindByIdQuery(CARDBFIELDS, CARDBNAME)
				, generatePutQuery(CARDBFIELDS, CARDBNAME)
				, generateDeleteQuery(CARDBNAME));
	}
	
	@Override
	protected Car parseResult(ResultSet rs) throws SQLException {
		Car car = new Car();
		car.setId(rs.getLong(fieldsMap.get("id")));
		car.setCode(rs.getString(fieldsMap.get("code")));
		car.setCarType(rs.getString(fieldsMap.get("carType")));
		car.setStatus(rs.getString(fieldsMap.get("status")));
		car.setOrgan(new Organization(rs.getLong(fieldsMap.get("organ"))));
		car.setCamNum(rs.getInt(fieldsMap.get("camNum")));
		car.setRegisterTime(rs.getLong(fieldsMap.get("registerTime")));
		car.setWeight(rs.getDouble(fieldsMap.get("weight")));
		car.setHeight(rs.getDouble(fieldsMap.get("height")));
		car.setLength(rs.getDouble(fieldsMap.get("length")));
		car.setPurchaseDate(rs.getLong(fieldsMap.get("purchaseDate")));
		car.setDriver2(new Driver(rs.getLong(fieldsMap.get("driver2"))));
		car.setDriver1(new Driver(rs.getLong(fieldsMap.get("driver1"))));		
		return car;
	}

	@Override
	protected void enrichPreparedStatement(PreparedStatement stmt, Car car) throws SQLException {
		int paramIndex = 1;
		stmt.setLong(paramIndex++, car.getId());
		stmt.setString(paramIndex++, car.getCode());
		stmt.setString(paramIndex++, car.getCarType());
		stmt.setString(paramIndex++, car.getStatus());
		stmt.setLong(paramIndex++, car.getOrgan().getId());
		stmt.setInt(paramIndex++, car.getCamNum());
		stmt.setLong(paramIndex++, car.getRegisterTime());
		stmt.setDouble(paramIndex++, car.getWeight());
		stmt.setDouble(paramIndex++, car.getHeight());
		stmt.setDouble(paramIndex++, car.getLength());
		stmt.setLong(paramIndex++, car.getPurchaseDate());
		stmt.setLong(paramIndex++, car.getDriver2().getId());
		stmt.setLong(paramIndex++, car.getDriver1().getId());
		
	}



}
