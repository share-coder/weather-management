package com.sapient.weather.dto.thirdparty.openweather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wind {
    private float speed;
    private int deg;

}
