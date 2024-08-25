package com.project.ridebooking.RideBookingApplication.Service.Impl;

import com.project.ridebooking.RideBookingApplication.Dto.*;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.*;
import com.project.ridebooking.RideBookingApplication.Entity.*;
import com.project.ridebooking.RideBookingApplication.Exception.ResourceNotFoundException;
import com.project.ridebooking.RideBookingApplication.Repository.DriverRepository;
import com.project.ridebooking.RideBookingApplication.Service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;


    @Override
    public RideDto cancelRide(Long rideId) {
        Ride currentRide = rideService.getRideById(rideId);
        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.equals(currentRide.getDriver())){
            throw new RuntimeException("Driver cannot cancel ride");
        }
        if(!currentRide.getRideStatus().equals(RideStatus.CONFIRMED)){
                throw new RuntimeException("Ride cannot be cancelled, invalid status "+currentRide.getRideStatus());
            }
        rideService.updateRideStatus(currentRide, RideStatus.CANCELLED);
        updateDriverAvailability(currentDriver, true);

        return modelMapper.map(currentRide, RideDto.class);
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
        paymentService.createNewPayment(savedRide);
        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    @Transactional
    public RideDto endRide(Long rideId) {
        Ride currentRide = rideService.getRideById(rideId);
        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.equals(currentRide.getDriver())){
            throw new RuntimeException("Driver cannot end the ride");
        }
        if(!currentRide.getRideStatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("Ride status is not ongoing, status: "+currentRide.getRideStatus());
        }
        currentRide.setEndTime(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(currentRide, RideStatus.ENDED);
        updateDriverAvailability(currentDriver, true);

        paymentService.processPayment(currentRide);
        return modelMapper.map(savedRide, RideDto.class);
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

        Driver savedDriver = updateDriverAvailability(currentDriver, false);

        Ride createdRide = rideService.createNewRide(rideRequest, savedDriver);
        return modelMapper.map(createdRide, RideDto.class);
    }

    @Override
    public DriverDto getMyProfile() {
        Driver currentDriver = getCurrentDriver();
        return modelMapper.map(currentDriver, DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver = getCurrentDriver();
        return rideService.getAllOfRidesOfDiver(currentDriver, pageRequest).map(
                ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public Driver getCurrentDriver() {
        return driverRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("Current driver not found"));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, Boolean availability) {
        driver.setAvailable(availability);
        return driverRepository.save(driver);
    }
}
