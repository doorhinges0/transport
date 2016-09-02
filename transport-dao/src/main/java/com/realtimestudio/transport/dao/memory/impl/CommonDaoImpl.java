package com.realtimestudio.transport.dao.memory.impl;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.realtimestudio.transport.dao.baisha.CommonDao;

//just for testing purpose
public abstract class CommonDaoImpl<V> implements CommonDao<Long, V> {
	public static final String ESCAPEDSEPERATOR = "\\|";
	protected ConcurrentSkipListMap<Long, V> map;
	protected File inputFile;
	
	public CommonDaoImpl(){}
	
	public CommonDaoImpl(String inputFilePath){
		Comparator<Long> comparator = new Comparator<Long>(){

			@Override
			public int compare(Long o1, Long o2) {
				return (int) (o2 - o1);
			}
			
		};
		map = new ConcurrentSkipListMap<>(comparator);
		inputFile = new File(inputFilePath);
		if(!inputFile.exists() || !inputFile.isFile()){
			throw new RuntimeException("The input file does not exist: " + inputFilePath);
		}
		
	
	}
	
	protected abstract V parseLine(String[] line) throws ParseException;
	protected abstract long getId(V object);
	protected abstract void setId(V object, long id);
	
	protected void parse(){
		
		LineIterator iterator = null;
		String line = null;
		try{
			iterator = FileUtils.lineIterator(inputFile);
			while(iterator.hasNext()){
				line = iterator.nextLine();
				V object = parseLine(line.split(ESCAPEDSEPERATOR, -1));
				map.put(getId(object), object);				
			}			
		}
		catch(Exception e){
			throw new RuntimeException("Can not parse line: " + line, e);
		}		
	}
	
	@Override
	public List<V> findAll() {
		return new ArrayList<V> (map.values());
	}
	
	@Override
	public V findById(Long key) {
		return map.get(key);
	}
	
	@Override
	public V put(Long key, V object) {
		map.put(key, object);
		return object;
	}
	
	@Override
	public synchronized V create(V object){
		long key = map.firstKey() + 1;
		setId(object, key);
		put(key, object);
		return object;
	}
	
	@Override
	public void delete(Long key){
		map.remove(key);
	}
	
	@Override
	public int getTotalNum(){
		return map.size();
	}
	
	@Override
	public List<V> findAllByPage(int pageNum, int itemsPerPage){
		List<V> allList = new ArrayList<V> (map.values());
		int len = allList.size();
		int start = (pageNum-1) * itemsPerPage;
		int end = start + itemsPerPage;
		
		start = start<0 ? 0 : start;
		end = end > len ? len : end;
		return allList.subList(start, end);		
	}
	

}
