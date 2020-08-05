package com.nextep.weather;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
	
	@Autowired
	private WeatherService service;
	
	@RequestMapping("/weather/{zipCode}")
	@ResponseBody
	public String getWeatherFor(@PathVariable String zipCode) throws IOException {
		
		return service.getWeatherFromS3(zipCode);
		
	}

}
