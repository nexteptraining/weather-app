package com.nextep.weather;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Repository
public class WeatherDataFeedRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	String SQL_INSERT = "insert into weather.temp_look_up values (?, ?)";
	
	public void populateFeedIntoDatabase() throws IOException {
		
		System.setProperty("AWS_ACCESS_KEY_ID", "AKIAQYDD5HW6W3ASPKON");
		System.setProperty("AWS_SECRET_ACCESS_KEY", "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");
		
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
		
		GetObjectRequest getObjectRequest = new GetObjectRequest("nextep-training-weather-data", "weather-data.csv");
		
		S3Object s3Object = s3.getObject(getObjectRequest);
		
		S3ObjectInputStream s3ObjIs = s3Object.getObjectContent();
		
		Reader targetReader = new InputStreamReader(s3ObjIs);
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(targetReader);
		
		Map<String, String> zipToTempMap = new HashMap<>();
		
		for (CSVRecord record : records) {
	        String zip = record.get(0);
	        String temp = record.get(1);
	        
	        zipToTempMap.put(zip, temp);
	    }
		
		jdbcTemplate.update("delete from weather.temp_look_up");
		
		for(Map.Entry<String, String> entry : zipToTempMap.entrySet()) {
			jdbcTemplate.update(SQL_INSERT, entry.getKey(), entry.getValue());
		}
	}

}
