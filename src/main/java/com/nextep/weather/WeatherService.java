package com.nextep.weather;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	String SQL_GET = "select temp from weather.temp_look_up where zip = ?";
	
	
	public String getWeatherFromS3(String zipCode) throws IOException {
		
		return jdbcTemplate.queryForObject(SQL_GET, new Object[] {zipCode}, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("temp");
			}
		});
		
	}

}
