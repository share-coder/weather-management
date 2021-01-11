package com.sapient.weather.dto.thirdparty.openweather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {
    private int id;
    private String name;
    private Coord coord;
    private String country;
    private long population;
    private long timeZone;
    private long sunRise;
    private long sunSet;

}
