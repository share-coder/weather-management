package com.sapient.weather.service.openweather;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.sapient.weather.common.exception.WeatherForecastException;
import com.sapient.weather.dto.request.WeatherRequestDto;
import com.sapient.weather.dto.response.FutureForecast;
import com.sapient.weather.dto.response.WeatherDto;
import com.sapient.weather.dto.thirdparty.openweather.ForecastResponse;
import com.sapient.weather.dto.thirdparty.openweather.Lst;
import com.sapient.weather.service.IWeatherForecast;
import com.sapient.weather.service.WeatherService;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Dedicated OpenWeather sort of service API
 * API: https://openweathermap.org/api
 *
 * @author Ayush_A
 */
@Component("OPEN_WEATHER")
@Log
public class OpenWeatherForecast implements IWeatherForecast<WeatherRequestDto, WeatherDto> {
    Logger logger = LoggerFactory.getLogger(OpenWeatherForecast.class);

    /**
     * weather endpoint. It can vary from customer to customer
     */
    @Value("${weather.endpoint}")
    private String weatherEndPoint;

    /**
     * API Key to connect to 3rd party weather app url
     * Ideally it should come from vault.
     */
    @Value("${weather.api.key}")
    private String apiKey;

    /**
     * Open Weather forecast service
     *
     * TODO: Add re-try mechanism in future using Apache HttpClient library
     *
     * @param dto Weather request dto which hold like city name
     * @return {@link WeatherDto} weather dto as response
     * @throws WeatherForecastException Weather forecast service exception
     */
    @Override
    public WeatherDto forecast(WeatherRequestDto dto) throws WeatherForecastException {
        logger.info("Open Weather service - forecast() - START WeatherRequestDto: {}", dto);
        WeatherDto weatherDto = new WeatherDto();
        try {
            //Call weather app third party api using Rest Template, we can also use HttpClient (Apache) library to have retry
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(weatherEndPoint + dto.getCity() + "&appid=" + apiKey, String.class);
            ForecastResponse forecastResponse = new Gson().fromJson(response.getBody(), ForecastResponse.class);

            List<Lst> list = forecastResponse.getList();
            Map<String, FutureForecast> forecastMap = new LinkedHashMap<>();

            /*
             Calculate min and max temp for each date and store in map where key is date and value is FutureForecast
             */
            float lowTemp = list.get(0).getMain().getTemp();
            float highTemp = list.get(0).getMain().getTemp();

            for (int i = 0; i < list.size(); i++) {
                if (forecastMap.size() == dto.getFutureForecastDays()) {
                    break;
                }
                Lst lst = list.get(i);
                //min, max logic
                float temp = lst.getMain().getTemp();
                if (temp < lowTemp) lowTemp = temp;
                if (temp > highTemp) highTemp = temp;

                //Rain condition
                boolean isRainyDay = false;
                if (forecastResponse.getList().get(i).getRain() != null) {
                    isRainyDay = ((LinkedTreeMap) forecastResponse.getList().get(i).getRain()).get("3h") != null;
                }

                //Put date wise forecast in forecastMap where key = date and value of FutureForecast
                //And update map if dealing with same date
                String dt = getDt(lst.getDt_txt());
                if (forecastMap.get(dt) == null) {
                    forecastMap.put(dt, new FutureForecast(dt, lowTemp, highTemp, isRainyDay));
                    lowTemp = temp;
                    highTemp = temp;
                } else {
                    FutureForecast futureForecast = forecastMap.get(dt);
                    futureForecast.setLowTemp(lowTemp);
                    futureForecast.setHighTemp(highTemp);
                    futureForecast.setIsRainyDay(isRainyDay);
                    forecastMap.put(dt, futureForecast);
                }
            }

            //Prediction logic - if all 3 days which we stored into forecastMap; are rainy day then
            // we need carry umbrella else sunscreen lotion
            List<FutureForecast> futureForecastList = new ArrayList<>();
            String prediction = calculatePrediction(forecastMap, futureForecastList);
            weatherDto.setForecast(futureForecastList);
            weatherDto.setPrediction(prediction);
        } catch (Exception e) {
            throw new WeatherForecastException("Error has occurred while calling Open Weather forecast service api, Error:", e);
        }
        return weatherDto;
    }

    private String calculatePrediction(Map<String, FutureForecast> forecastMap, List<FutureForecast> futureForecastList) {
        boolean isRainyDay = true;
        String prediction;
        for (Map.Entry<String, FutureForecast> entry : forecastMap.entrySet()) {
            FutureForecast value = entry.getValue();
            //concatenation
            isRainyDay &= value.getIsRainyDay();
            value.setIsRainyDay(null);
            futureForecastList.add(value);
        }
        if (isRainyDay) {
            prediction = "Carry umbrella";
        } else {
            prediction = "Use sunscreen lotion";
        }
        return prediction;
    }
}
