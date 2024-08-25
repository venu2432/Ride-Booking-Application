package com.project.ridebooking.RideBookingApplication.Service.Impl;

import com.project.ridebooking.RideBookingApplication.Dto.DriverDto;
import com.project.ridebooking.RideBookingApplication.Dto.RiderDto;
import com.project.ridebooking.RideBookingApplication.Entity.Driver;
import com.project.ridebooking.RideBookingApplication.Entity.Rating;
import com.project.ridebooking.RideBookingApplication.Entity.Ride;
import com.project.ridebooking.RideBookingApplication.Entity.Rider;
import com.project.ridebooking.RideBookingApplication.Exception.ResourceNotFoundException;
import com.project.ridebooking.RideBookingApplication.Repository.DriverRepository;
import com.project.ridebooking.RideBookingApplication.Repository.RatingRepository;
import com.project.ridebooking.RideBookingApplication.Repository.RiderRepository;
import com.project.ridebooking.RideBookingApplication.Service.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final RatingRepository ratingRepository;
    private final ModelMapper modelMapper;


    @Override
    public DriverDto rateDriver(Ride ride, Integer rating) {
        Driver driver = ride.getDriver();
        Rating ratingObject = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id "+ride.getId()));

        if(ratingObject.getDriverRating() != null)
            throw new RuntimeException("Driver has already been rated");

        ratingObject.setDriverRating(rating);
        ratingRepository.save(ratingObject);

        Double updatedRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(Rating :: getDriverRating)
                .average().orElse(0.0);
        driver.setRating(updatedRating);
        Driver savedDriver = driverRepository.save(driver);
        return modelMapper.map(savedDriver, DriverDto.class);
    }

    @Override
    public RiderDto rateRider(Ride ride, Integer rating) {
        Rider rider = ride.getRider();
        Rating ratingObject = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id "+ride.getId()));

        if(ratingObject.getRiderRating() != null)
            throw new RuntimeException("Rider has already been rated");

        ratingObject.setRiderRating(rating);
        ratingRepository.save(ratingObject);

        Double updatedRating = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(Rating :: getRiderRating)
                .average().orElse(0.0);
        rider.setRating(updatedRating);

        Rider savedRider = riderRepository.save(rider);
        return modelMapper.map(savedRider, RiderDto.class);
    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating = Rating.builder()
                .ride(ride)
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .build();

        ratingRepository.save(rating);
    }

}
