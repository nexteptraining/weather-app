package com.nextep.weather;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherDataFeedController {

	@Autowired
	private WeatherDataFeedRepository dao;
	
	@PutMapping("/populate/weatherdata")
	public void populateWeatherData() throws IOException {
		dao.populateFeedIntoDatabase();
	}
}
