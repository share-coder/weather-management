package com.sapient.weather.dto.thirdparty.openweather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lst {
    private long dt;
    private Main main;
    private List<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private long visibility;
    private float pop;
    private Object rain;
    private Sys sys;
    private String dt_txt;
}
