package com.realtimestudio.transport.dao.phoenix.impl;

import static com.realtimestudio.transport.dao.baisha.Constants.ORGANIZATIONDBFIELDS;
import static com.realtimestudio.transport.dao.baisha.Constants.ORGANIZATIONDBNAME;
import static com.realtimestudio.transport.dao.baisha.Constants.ORGANIZATIONENTITYFIELDS;
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
import com.realtimestudio.transport.dao.baisha.OrganizationDao;
import com.realtimestudio.transport.model.baisha.Organization;

public class OrganizationDaoImpl extends CommonDaoImpl<Organization> implements OrganizationDao{
	private static Map<String, String> fieldsMap = getEntity2DBFieldsMap(ORGANIZATIONENTITYFIELDS, ORGANIZATIONDBFIELDS);

	public OrganizationDaoImpl(ConnectionManager<Connection> connectionManager) {
		super(connectionManager
				, generateFindAllQuery(ORGANIZATIONDBFIELDS, ORGANIZATIONDBNAME)
				, generateFindByIdQuery(ORGANIZATIONDBFIELDS, ORGANIZATIONDBNAME)
				, generatePutQuery(ORGANIZATIONDBFIELDS, ORGANIZATIONDBNAME)
				, generateDeleteQuery(ORGANIZATIONDBNAME));
	}
	
	@Override
	protected Organization parseResult(ResultSet rs) throws SQLException {
		Organization organ = new Organization();
		organ.setId(rs.getLong(fieldsMap.get("id")));
		organ.setType(rs.getString(fieldsMap.get("type")));
		organ.setParent(new Organization(rs.getLong("parent")));
		organ.setName(rs.getString(fieldsMap.get("name")));
		return organ;
	}

	@Override
	protected void enrichPreparedStatement(PreparedStatement stmt, Organization organ) throws SQLException {
		int paramIndex = 1;
		stmt.setLong(paramIndex++, organ.getId());
		stmt.setString(paramIndex++, organ.getType());
		stmt.setLong(paramIndex++, organ.getParent().getId());
		stmt.setString(paramIndex++, organ.getName());
		
	}

}
