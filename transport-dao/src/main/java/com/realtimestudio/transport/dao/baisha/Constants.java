package com.realtimestudio.transport.dao.baisha;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Constants {
	public final static String[] CARDBFIELDS = {"id", "code", "car_type", "status"
			,"organ", "cam_num", "register_time", "weight", "height", "length"
			,"car_purchase_date", "driver2", "driver1"};
	public final static String[] CARENTITYFIELDS = {"id", "code", "carType", "status"
			,"organ", "camNum", "registerTime", "weight", "height", "length"
			,"purchaseDate", "driver2", "driver1"};
	public final static String CARDBNAME = "baisha_car";
	
	public final static String[] ORGANIZATIONDBFIELDS = {"id", "type", "parentid", "name"};
    public final static String[] ORGANIZATIONENTITYFIELDS = {"id", "type", "parent", "name"};
    public final static String ORGANIZATIONDBNAME = "baisha_organization";
    
    public final static String[] DRIVERDBFIELDS = {"id", "name", "address", "phone_num"
    		,"organ", "driver_license", "registration_time", "dob", "gender"};
    public final static String[] DRIVERENTITYFIELDS = {"id", "name", "address", "phoneNum"
    		,"organ", "driverLicense", "registrationDate", "dob", "gender"};
    public final static String DRIVERDBNAME = "baisha_driver";
    public final static String DOBFORMAT = "yyyyMMdd";
    
    public final static String[] ROUTEDBFIELDS = {"id", "operation_type", "car", "plan_date", "contract_no"
    		,"start_place", "dest_prov", "dest_city", "dest_place", "product_num", "product_info"
    		,"driver1", "driver2", "start_time", "arrival_time", "finish_time", "alarm_no"};
    public final static String[] ROUTEENTITYFIELDS = {"id", "operationType", "car", "planDate", "contractNo"
    		,"startPlace", "destProv", "destCity", "destPlace", "productNum", "productInfo"
    		,"driver1", "driver2", "startTime", "arrivalTime", "finishTime", "alarmNo"};
    public final static String ROUTEDBNAME = "baisha_route";

    public final static String[] ROUTEPOINTDBFIELDS = {"id", "routeid", "carid", "speed", "longitude"
    		,"latitude" , "direction", "timestamp", "events"};
    public final static String[] ROUTEPOINTENTITYFIELDS = {"id", "route", "car", "speed", "longitude"
    		,"latitude", "direction", "timestamp", "events"};
    public final static String TRACKALARMDBNAME = "baisha_track_alarm";

    
    public static String generateFindAllQuery(String[] dbFields, String tableName){
    	StringBuilder builder = new StringBuilder("SELECT ");
    	builder.append(StringUtils.join(dbFields, ','));
    	builder.append(" FROM " + tableName);
    	return builder.toString();
    }
    
    public static String generateFindByIdQuery(String[] dbFields, String tableName){
    	StringBuilder builder = new StringBuilder("SELECT ");
    	builder.append(StringUtils.join(dbFields, ','));
    	builder.append(" FROM " + tableName + " WHERE id=?");
    	return builder.toString();
    }
    
    public static String generatePutQuery(String[] dbFields, String tableName){
    	StringBuilder builder = new StringBuilder("UPSERT INTO " + tableName + "(");
    	builder.append(StringUtils.join(dbFields, ','));
    	builder.append(") VALUES (");
    	for(int i =0; i<dbFields.length; i++){
    		if(i>0) builder.append(",");
    		builder.append("?");    		
    	}
    	builder.append(")");
    	return builder.toString();    	
    }
    
    public static String generateDeleteQuery(String tableName){
    	return "DELETE FROM " + tableName + " WHERE id=?";
    }  
    
    public static Map<String, String> getEntity2DBFieldsMap(String[] entityFields, String[] dbFields){
    	Map<String, String> map = new HashMap<>();
    	for(int i=0; i<entityFields.length; i++){
    		map.put(entityFields[i], dbFields[i]);
    	}
    	return map;
    }
    
    public static void main(String[] args){
    	System.out.println(generateFindAllQuery(CARDBFIELDS, CARDBNAME));
    	System.out.println(generateFindByIdQuery(CARDBFIELDS, CARDBNAME));
    	System.out.println(generatePutQuery(CARDBFIELDS, CARDBNAME));
    	System.out.println(generateDeleteQuery(CARDBNAME));
    	System.out.println(getEntity2DBFieldsMap(CARENTITYFIELDS, CARDBFIELDS));
    }
}
