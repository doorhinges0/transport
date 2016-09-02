package com.realtimestudio.transport.dao.baisha;

import java.util.List;
import java.util.Map;

public interface CommonDao<K, V> {
	List<V> findAll();
	V findById(K key);
	V put(K key, V t);
	V create(V t);
	void delete(K key);
	
	List<V> findAllByPage(int pageNum, int itemsPerPage);
	int getTotalNum();
}
