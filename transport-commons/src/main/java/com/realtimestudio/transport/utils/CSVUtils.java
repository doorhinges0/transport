package com.realtimestudio.transport.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class CSVUtils {
	public static List<Map<String, String>> getRecordsFromFile(Reader reader, String[] header_mapping, boolean skipHeader) throws FileNotFoundException, IOException{
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(header_mapping).withSkipHeaderRecord(skipHeader);
		List<Map<String,String>> result = new ArrayList<>();

		try(BufferedReader in = new BufferedReader(reader);
				CSVParser parser = new CSVParser(in, csvFileFormat)){
			
			for(CSVRecord record : parser){
				result.add(record.toMap());
			}			
		}
		return result;
	}
	
	public static List<Map<String, String>> getRecordsFromFile(File file, String[] header_mapping, boolean skipHeader) throws FileNotFoundException, IOException{
		return getRecordsFromFile(new FileReader(file), header_mapping, skipHeader);
	}
	
	public static void cleanWhiteSpaces(File inputFile, File outputFile, String seperatorEscaped, String seperator) throws IOException{
		LineIterator iterator = FileUtils.lineIterator(inputFile, "UTF-8");
		while(iterator.hasNext()){
			String line = iterator.nextLine();
			String lineWOSpaces = removeWhiteSpaceFromLine(line, seperatorEscaped, seperator)+"\n";
			FileUtils.writeStringToFile(outputFile, lineWOSpaces, "UTF-8", true);
		}
		
	}
	
	public static String removeWhiteSpaceFromLine(String line, String seperatorEscaped, String seperator){
		String[] parts = line.split(seperatorEscaped, -1);
		StringBuilder builder = new StringBuilder(parts[0].trim());
		for(int i=1; i<parts.length; i++){
			builder.append(seperator);
			builder.append(parts[i].trim());
		}
		return builder.toString();		
	}
	
	public static void main(String[] args) throws IOException{
		String[] fileNames = {"/tmp/organizations.csv", "/tmp/drivers.csv"
				,"/tmp/cars.csv", "/tmp/routes.csv"};
		
		for(String fileName : fileNames){
			File inputFile = new File(fileName);
			File outputFile = new File(fileName+"_output");
			cleanWhiteSpaces(inputFile, outputFile, "\\|", "|");
		}
		
				       
		
	}

}
