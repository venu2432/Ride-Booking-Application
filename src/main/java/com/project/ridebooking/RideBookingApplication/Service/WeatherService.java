package com.project.ridebooking.RideBookingApplication.Service;

import org.locationtech.jts.geom.Point;

import java.util.concurrent.CompletableFuture;

public interface WeatherService {
    public CompletableFuture<Double> getWeatherMultiplier(Point point);
}
