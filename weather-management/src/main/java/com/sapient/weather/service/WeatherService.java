package com.sapient.weather.service;

import com.sapient.weather.dto.request.WeatherRequestDto;
import com.sapient.weather.dto.response.WeatherDto;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Weather forecast Service
 * It can delegate to other service
 *
 * @author Ayush_A
 */
@Service
@Log
public class WeatherService {
    Logger logger = LoggerFactory.getLogger(WeatherService.class);

    /***
     * Lets have strategy to pick 3rd party weather service.
     */
    private final Map<String, IWeatherForecast<WeatherRequestDto, WeatherDto>> weatherForecastServiceMap;

    /**
     * Third party weather service type
     * It can be any and set in configuration before deployment and third party service api wll be used accordingly
     */
    @Value("${weather.service.type}")
    private String thirdPartyServiceType;

    public WeatherService(Map<String, IWeatherForecast<WeatherRequestDto, WeatherDto>> weatherForecastServiceMap) {
        this.weatherForecastServiceMap = weatherForecastServiceMap;
    }

    /**
     * Calculate weather forecast form given future days inclusive current date
     * This service method fetch weather forecast data from third party api and calculate forecast
     * <p>
     * TODO: Invalidation goes here, can be done in future!
     * @param cityName     Name of city for forecast
     * @param forecastDays future forecast days
     * @return Weather Dto
     * @throws Exception If any
     */
    public WeatherDto calculateForecast(String cityName, int forecastDays) throws Exception {
        logger.info("calculateForecast - START CityName: {}, forecastDays: {}", cityName, forecastDays);
        logger.info("Third party service type: {}", thirdPartyServiceType);
        IWeatherForecast<WeatherRequestDto, WeatherDto> weatherForecast = weatherForecastServiceMap.get(thirdPartyServiceType);
        return weatherForecast.forecast(new WeatherRequestDto(cityName, forecastDays));
    }
}
