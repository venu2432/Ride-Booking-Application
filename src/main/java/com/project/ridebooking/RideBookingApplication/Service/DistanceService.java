package com.project.ridebooking.RideBookingApplication.Service;

import org.locationtech.jts.geom.Point;

public interface DistanceService {

    public Double calculateDistance(Point src, Point dest);

}
