package com.realtimestudio.transport.utils;

import java.io.Reader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public static String toString(Object obj){
		return gson.toJson(obj);
	}
	

}
