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
    private final RatingService ratingService;


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
        ratingService.createNewRating(savedRide);

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
        Ride ride =  rideService.getRideById(rideId);
        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot rate the ride");
        }
        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride status is not ended, status: "+ride.getRideStatus());
        }
        return ratingService.rateRider(ride, rating);
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

    @Override
    public Driver createNewDriver(Driver driver) {
        return driverRepository.save(driver);
    }

}
