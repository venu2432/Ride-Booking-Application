package com.project.ridebooking.RideBookingApplication.Repository;

import com.project.ridebooking.RideBookingApplication.Entity.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {
}
