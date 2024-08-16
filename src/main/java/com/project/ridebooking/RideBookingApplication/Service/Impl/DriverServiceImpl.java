package com.project.ridebooking.RideBookingApplication.Service.Impl;

import com.project.ridebooking.RideBookingApplication.Dto.DriverDto;
import com.project.ridebooking.RideBookingApplication.Dto.RideDto;
import com.project.ridebooking.RideBookingApplication.Dto.RiderDto;
import com.project.ridebooking.RideBookingApplication.Entity.Driver;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.RideRequestStatus;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.RideStatus;
import com.project.ridebooking.RideBookingApplication.Entity.Ride;
import com.project.ridebooking.RideBookingApplication.Entity.RideRequest;
import com.project.ridebooking.RideBookingApplication.Exception.ResourceNotFoundException;
import com.project.ridebooking.RideBookingApplication.Repository.DriverRepository;
import com.project.ridebooking.RideBookingApplication.Repository.RideRepository;
import com.project.ridebooking.RideBookingApplication.Service.DriverService;
import com.project.ridebooking.RideBookingApplication.Service.RideRequestService;
import com.project.ridebooking.RideBookingApplication.Service.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final RideRepository rideRepository;

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto startRide(Long rideId, String otp) {
        Ride currentRide = rideService.getRideById(rideId);
        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.equals(currentRide.getDriver())){
            throw new RuntimeException("Driver cannot start ride");
        }
        if(!currentRide.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride status is not confirmed, status: "+currentRide.getRideStatus());
        }
        if(!otp.equals(currentRide.getOtp())){
            throw new RuntimeException("Ride cannot be accepted due to wrong otp");
        }
        currentRide.setStartTime(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(currentRide, RideStatus.ONGOING);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RideDto endRide(Long rideId) {
        return null;
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    @Transactional
    public RideDto acceptRide(Long rideId) {
        RideRequest rideRequest = rideRequestService.getRideRequest(rideId);
        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("Ride request cannot be accepted, status is:"+ rideRequest.getRideRequestStatus());
        }
        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.getAvailable()){
            throw new RuntimeException("Current driver cannot accept ride due to unavailability");
        }
        currentDriver.setAvailable(false);
        Driver savedDriver = driverRepository.save(currentDriver);

        Ride createdRide = rideService.createNewRide(rideRequest, savedDriver);
        return modelMapper.map(createdRide, RideDto.class);
    }

    @Override
    public DriverDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
    }

    @Override
    public Driver getCurrentDriver() {
        return driverRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("Current driver not found"));
    }
}
