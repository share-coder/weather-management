package com.sapient.weather.dto.thirdparty.openweather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForecastResponse {
    private String cod;
    private long message;
    private int cnt;
    private List<Lst> list;
    private City city;
}
