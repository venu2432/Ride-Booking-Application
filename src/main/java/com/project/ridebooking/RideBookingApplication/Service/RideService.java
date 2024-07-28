package com.project.ridebooking.RideBookingApplication.Service;

import com.project.ridebooking.RideBookingApplication.Dto.RideRequestDto;
import com.project.ridebooking.RideBookingApplication.Entity.Driver;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.RideStatus;
import com.project.ridebooking.RideBookingApplication.Entity.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {

    Ride getRideById(Long id);

    void matchWithDiver(RideRequestDto rideRequestDto);

    Ride createNewRide(RideRequestDto rideRequestDto, Driver driver);

    Ride updateRideStatus(Long rideId, RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Long riderId, PageRequest pageRequest);

    Page<Ride> getAllOfRiderOfDiver(Long driverId, PageRequest pageRequest);

}
