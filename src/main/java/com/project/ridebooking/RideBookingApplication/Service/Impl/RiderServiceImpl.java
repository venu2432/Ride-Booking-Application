package com.project.ridebooking.RideBookingApplication.Service.Impl;

import com.project.ridebooking.RideBookingApplication.Dto.DriverDto;
import com.project.ridebooking.RideBookingApplication.Dto.RideDto;
import com.project.ridebooking.RideBookingApplication.Dto.RideRequestDto;
import com.project.ridebooking.RideBookingApplication.Dto.RiderDto;
import com.project.ridebooking.RideBookingApplication.Entity.*;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.RideRequestStatus;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.RideStatus;
import com.project.ridebooking.RideBookingApplication.Exception.ResourceNotFoundException;
import com.project.ridebooking.RideBookingApplication.Repository.RideRequestRepository;
import com.project.ridebooking.RideBookingApplication.Repository.RiderRepository;
import com.project.ridebooking.RideBookingApplication.Service.*;
import com.project.ridebooking.RideBookingApplication.Strategy.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;
    private final WeatherService weatherService;


    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) throws ExecutionException, InterruptedException {
        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        List<Driver> drivers = rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDriver(savedRideRequest);

        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Rider currentRider = getCurrentRider();
        Ride currentRide = rideService.getRideById(rideId);
        if(!currentRide.getRider().equals(currentRider)){
            throw new RuntimeException("Ride does not own this ride to cancel");
        }
        if(!currentRide.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be cancelled, invalid status "+currentRide.getRideStatus());
        }
        Ride savedRide = rideService.updateRideStatus(currentRide, RideStatus.CANCELLED);

        driverService.updateDriverAvailability(currentRide.getDriver(), true);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        Ride ride =  rideService.getRideById(rideId);
        Rider rider = getCurrentRider();
        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider cannot rate the ride");
        }
        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride status is not ended, status: "+ride.getRideStatus());
        }
        return ratingService.rateDriver(ride, rating);
    }

    @Override
    public RiderDto getMyProfile() {
        Rider rider = getCurrentRider();
        return modelMapper.map(rider, RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider rider = getCurrentRider();
        return rideService.getAllRidesOfRider(rider, pageRequest).map(
                ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public Rider createNewRide(User user){
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();

        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return riderRepository.findByUser(user).orElseThrow(() ->
                new ResourceNotFoundException("Rider not associated with this id: "+user.getId()));
    }
}
