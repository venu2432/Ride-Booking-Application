package com.project.ridebooking.RideBookingApplication.Repository;

import com.project.ridebooking.RideBookingApplication.Entity.Driver;
import com.project.ridebooking.RideBookingApplication.Entity.Rating;
import com.project.ridebooking.RideBookingApplication.Entity.Ride;
import com.project.ridebooking.RideBookingApplication.Entity.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByRider(Rider rider);
    List<Rating> findByDriver(Driver driver);
    Optional<Rating> findByRide(Ride ride);
}
