package com.project.ridebooking.RideBookingApplication.Service.Impl;


import com.project.ridebooking.RideBookingApplication.Dto.WeatherDto;
import com.project.ridebooking.RideBookingApplication.Service.WeatherService;
import com.project.ridebooking.RideBookingApplication.Strategy.WeatherCategorizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    @Value("${weather.apikey}")
    private String apiKey;

    @Override
    @Async
    public CompletableFuture<Double> getWeatherMultiplier(Point point) {
        Double currentMultiplier = 1.0;
        String url = "https://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q="+point.getY()+","+point.getX();

        WeatherDto response = RestClient.builder()
                .baseUrl(url)
                .build()
                .get()
                .retrieve()
                .body(WeatherDto.class);
        Integer code = response.getCurrent().getCondition().getCode();
        String condition = String.valueOf(WeatherCategorizer.categorizeWeather(code));
        if(condition.equals("AVERAGE")){
            //average weather conditions
            currentMultiplier = currentMultiplier * 1.5;
        }
        else if(condition.equals("BAD")){
            //bad weather conditions
            currentMultiplier = currentMultiplier * 2;
        }
        return CompletableFuture.completedFuture(currentMultiplier);
    }
}

