package com.sapient.weather.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherRequestDto implements Serializable {
    private String city;
    private int futureForecastDays;
}
