package com.project.ridebooking.RideBookingApplication.Repository;

import com.project.ridebooking.RideBookingApplication.Entity.Driver;
import com.project.ridebooking.RideBookingApplication.QueryConstants.QueryConstant;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query(value = QueryConstant.FIND_MATCHING_DRIVERS, nativeQuery = true)
    List<Driver> findTenNearestDrivers(Point pickupLocation);

    @Query(value = QueryConstant.FIND_NEAREST_TOP_RATED_DRIVERS, nativeQuery = true)
    List<Driver> findTenNearByTopRatedDrivers(Point pickupLocation);

}
