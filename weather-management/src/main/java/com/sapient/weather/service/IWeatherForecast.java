package com.sapient.weather.service;

import com.sapient.weather.common.exception.WeatherForecastException;

import java.text.ParseException;
import java.util.Calendar;

import static com.sapient.weather.common.constants.ApiConstant.SIMPLE_DATE_FORMAT;

public interface IWeatherForecast<I, O> {
    public O forecast(I dto) throws WeatherForecastException, ParseException;

    /**
     * Extract date except time from date string
     * Note: In multithreaded env we can get SimpleDateFormat per thread using ThreadLocal
     * <p>
     *
     * @param dateText date text in format yyyy-mm-dd hh:mm:ss
     * @return extracted date without time
     * @throws ParseException in case error
     */
    default String getDt(String dateText) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(SIMPLE_DATE_FORMAT.parse(dateText));
        return calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }
}
