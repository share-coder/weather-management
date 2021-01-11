package com.sapient.weather.service.someother;

import com.sapient.weather.common.exception.WeatherForecastException;
import com.sapient.weather.dto.request.WeatherRequestDto;
import com.sapient.weather.dto.response.WeatherDto;
import com.sapient.weather.service.IWeatherForecast;
import com.sapient.weather.service.openweather.OpenWeatherForecast;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("SOME_OTHER")
@Log
public class SomeOtherForecast implements IWeatherForecast<WeatherRequestDto, WeatherDto> {
    Logger logger = LoggerFactory.getLogger(OpenWeatherForecast.class);

    @Override
    public WeatherDto forecast(WeatherRequestDto dto) throws WeatherForecastException {
        logger.info("SomeOther Weather service - forecast() - START WeatherRequestDto: {}", dto);
        return null;
    }
}