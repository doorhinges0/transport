package com.realtimestudio.transport.dao.phoenix.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.realtimestudio.transport.dao.baisha.CommonDao;
import com.realtimestudio.transport.dao.ConnectionManager;

public abstract class CommonDaoImpl<V> implements CommonDao<Long, V> {
	private ConnectionManager<Connection> connectionManager;
	private String findAllQuery, findByIdQuery, putQuery, deleteQuery;
	
	protected abstract V parseResult(ResultSet rs) throws Exception;
	protected abstract void enrichPreparedStatement(PreparedStatement stmt, V object) throws SQLException;
	
	public CommonDaoImpl(ConnectionManager<Connection> connectionManager
			, String findAllQuery, String findByIdQuery
	        , String putQuery, String deleteQuery) {
		this.connectionManager = connectionManager;
		this.findAllQuery = findAllQuery;
		this.findByIdQuery = findByIdQuery;
		this.putQuery = putQuery;
		this.deleteQuery = deleteQuery;
	}
	
	@Override
	public List<V> findAll() {
		Connection connection = connectionManager.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(findAllQuery);
				ResultSet rs = stmt.executeQuery();){			
			List<V> list = new ArrayList<>();
			while(rs.next()){
				list.add(parseResult(rs));
			}
			return list;
		} 
		catch(Exception e){
			throw new RuntimeException(e);
		}
		finally{
			connectionManager.releaseConnection(connection);
		}
	}

	@Override
	public V findById(Long key) {
		Connection connection = connectionManager.getConnection();
		try(PreparedStatement stmt = connection.prepareStatement(findByIdQuery);) {
			stmt.setLong(1,  key);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				return parseResult(rs);
			}
			return null;
			
		} 
		catch(Exception e){
			throw new RuntimeException(e);
		}
		finally{
			connectionManager.releaseConnection(connection);
		}
	}
	

	@Override
	public V put(Long key, V object) {
		Connection connection = connectionManager.getConnection();
		try(PreparedStatement stmt = connection.prepareStatement(putQuery);) {
			enrichPreparedStatement(stmt, object);
			stmt.executeUpdate();
			connection.commit();
			return object;
		} 
		catch(SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			connectionManager.releaseConnection(connection);
		}		
		
	}
	
	@Override
	public void delete(Long key){
		Connection connection = connectionManager.getConnection();
		try(PreparedStatement stmt = connection.prepareStatement(deleteQuery)) {
			stmt.setLong(1, key);
			stmt.executeUpdate();
			connection.commit();
		} 
		catch(SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			connectionManager.releaseConnection(connection);
		}			
	}	
	
	@Override
	public int getTotalNum(){
		return 0;
	}
	
	@Override
	public List<V> findAllByPage(int pageNum, int itemsPerPage){
		return null;	
	}
	
	@Override
	public V create(V t){
		return null;
	}
	


}
