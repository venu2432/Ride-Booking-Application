package com.project.ridebooking.RideBookingApplication.Repository;

import com.project.ridebooking.RideBookingApplication.Entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
}
