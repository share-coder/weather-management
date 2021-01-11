package com.sapient.weather.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FutureForecast {
    private String date;
    private float lowTemp;
    private float highTemp;
    private Boolean isRainyDay;
}
