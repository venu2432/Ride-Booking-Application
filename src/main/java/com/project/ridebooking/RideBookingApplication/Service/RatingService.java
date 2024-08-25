package com.project.ridebooking.RideBookingApplication.Service;

import com.project.ridebooking.RideBookingApplication.Dto.DriverDto;
import com.project.ridebooking.RideBookingApplication.Dto.RiderDto;
import com.project.ridebooking.RideBookingApplication.Entity.Ride;

public interface RatingService {

    DriverDto rateDriver(Ride rride, Integer rating);

    RiderDto rateRider(Ride ride, Integer rating);

    void createNewRating(Ride ride);

}
