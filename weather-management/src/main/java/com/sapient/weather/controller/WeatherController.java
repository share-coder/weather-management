package com.sapient.weather.controller;

import com.sapient.weather.dto.response.WeatherDto;
import com.sapient.weather.service.WeatherService;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Weather forecast Controller
 *
 * @author Ayush_A
 */
@RestController
@RequestMapping("/v1")
@Log
public class WeatherController {
    Logger logger = LoggerFactory.getLogger(WeatherController.class);
    private WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Find weather forecast
     * TODO: Use Advise to handle exception handling and localization, in future
     * @param city         City name for forecast
     * @param forecastDays Number of days for forecast
     * @return {@link WeatherDto} response object.
     * @throws Exception In case any error
     */
    @GetMapping(value = "/weathers/forecast")
    public ResponseEntity<WeatherDto> checkWeather(@RequestParam(value = "city") String city,
                                                   @RequestParam(value = "forecastDays") Integer forecastDays) throws Exception {
        logger.info("checkWeather Controller - START city: {}, forecastDays: {}", city, forecastDays);
        WeatherDto weatherDto = weatherService.calculateForecast(city, forecastDays);
        logger.info("Weather forecast calculated successfully!");
        return new ResponseEntity<>(weatherDto, HttpStatus.OK);
    }
}
