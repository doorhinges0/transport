package com.realtimestudio.transport.web.service;

import java.util.List;


public interface CommonService<V> {
	List<V> findAll();
	V findById(long id);
	List<V> findByPage(int pageNum, int itemsPerPage);
	V create(V object);
	V update(long id, V object);

}
