package com.project.ridebooking.RideBookingApplication.Service.Impl;

import com.project.ridebooking.RideBookingApplication.Entity.RideRequest;
import com.project.ridebooking.RideBookingApplication.Exception.ResourceNotFoundException;
import com.project.ridebooking.RideBookingApplication.Repository.RideRepository;
import com.project.ridebooking.RideBookingApplication.Repository.RideRequestRepository;
import com.project.ridebooking.RideBookingApplication.Service.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;
    private final RideRepository rideRepository;

    @Override
    public RideRequest getRideRequest(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId).orElseThrow(() -> new ResourceNotFoundException("Ride request not found with id: "+rideRequestId));
    }

    @Override
    public void update(RideRequest rideRequest) {
        RideRequest rideToSave = rideRequestRepository.findById(rideRequest.getId()).orElseThrow(() ->
            new ResourceNotFoundException("RideRequest not found with id: "+rideRequest.getId()));
        rideRequestRepository.save(rideRequest);
    }
}
