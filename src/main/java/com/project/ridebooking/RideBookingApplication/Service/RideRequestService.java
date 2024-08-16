package com.project.ridebooking.RideBookingApplication.Service;

import com.project.ridebooking.RideBookingApplication.Entity.RideRequest;

public interface RideRequestService {

    RideRequest getRideRequest(Long rideRequestId);

    void update(RideRequest rideRequest);
}
