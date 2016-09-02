package com.realtimestudio.transport.dao.phoenix.impl;

import static com.realtimestudio.transport.dao.baisha.Constants.DOBFORMAT;
import static com.realtimestudio.transport.dao.baisha.Constants.DRIVERDBFIELDS;
import static com.realtimestudio.transport.dao.baisha.Constants.DRIVERDBNAME;
import static com.realtimestudio.transport.dao.baisha.Constants.DRIVERENTITYFIELDS;
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
import com.realtimestudio.transport.dao.baisha.DriverDao;
import com.realtimestudio.transport.model.baisha.Driver;
import com.realtimestudio.transport.model.baisha.Gender;
import com.realtimestudio.transport.model.baisha.Organization;
import com.realtimestudio.transport.utils.DateUtils;

public  class DriverDaoImpl extends CommonDaoImpl<Driver> implements DriverDao{
	private static Map<String, String> fieldsMap = getEntity2DBFieldsMap(DRIVERENTITYFIELDS, DRIVERDBFIELDS);

	public DriverDaoImpl(ConnectionManager<Connection> connectionManager) {
		super(connectionManager
				, generateFindAllQuery(DRIVERDBFIELDS, DRIVERDBNAME)
				, generateFindByIdQuery(DRIVERDBFIELDS, DRIVERDBNAME)
				, generatePutQuery(DRIVERDBFIELDS, DRIVERDBNAME)
				, generateDeleteQuery(DRIVERDBNAME));
	}

	@Override
	protected Driver parseResult(ResultSet rs) throws Exception {
		Driver driver = new Driver();
		driver.setId(rs.getLong(fieldsMap.get("id")));
		driver.setName(rs.getString(fieldsMap.get("name")));
		driver.setPhoneNum(rs.getString(fieldsMap.get("phoneNum")));
		driver.setOrgan(new Organization(rs.getLong(fieldsMap.get("organ"))));
		driver.setDriverLicense(rs.getString(fieldsMap.get("driverLicense")));
		driver.setRegistrationDate((rs.getLong("registrationDate")));
		driver.setDob(DateUtils.getDate(rs.getString(fieldsMap.get("dob")), DOBFORMAT));
		driver.setGender(Gender.getGender(rs.getInt(fieldsMap.get("gender"))));
		return driver;
	}

	@Override
	protected void enrichPreparedStatement(PreparedStatement stmt, Driver driver) throws SQLException {
		int paramIndex = 1;
		stmt.setLong(paramIndex++ , driver.getId());
		stmt.setString(paramIndex++ , driver.getName());
		stmt.setString(paramIndex++ , driver.getAddress());
		stmt.setString(paramIndex++ , driver.getPhoneNum());
		stmt.setLong(paramIndex++ , driver.getOrgan().getId());
		stmt.setString(paramIndex++ , driver.getDriverLicense());
		stmt.setLong(paramIndex++ , driver.getRegistrationDate());
		stmt.setString(paramIndex++ , DateUtils.getDateStr(driver.getDob(), DOBFORMAT));
		stmt.setInt(paramIndex++ , driver.getGender().getLabel());		
	}

}
