package com.sapient.weather.dto.thirdparty.openweather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Main {
    private float temp;
    private float feels_like;
    private float temp_min;
    private float temp_max;
    private int pressure;
    private int sea_level;
    private int grnd_level;
    private int humidity;
    private float temp_kf;
}
