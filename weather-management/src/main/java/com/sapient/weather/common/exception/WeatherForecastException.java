package com.sapient.weather.common.exception;

public class WeatherForecastException extends RuntimeException {
    public WeatherForecastException(){

    }

    public WeatherForecastException(String msg){
        super(msg);
    }

    public WeatherForecastException(String msg, Throwable th){
        super(msg, th);
    }
}
