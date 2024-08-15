package com.project.ridebooking.RideBookingApplication.Service;

import org.locationtech.jts.geom.Point;

public interface DistanceService {

    Double RIDE_FARE_MULTIPLIER = 10.0;

    public Double calculateDistance(Point src, Point dest);

}
