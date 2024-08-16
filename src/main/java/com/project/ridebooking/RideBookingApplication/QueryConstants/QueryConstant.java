package com.project.ridebooking.RideBookingApplication.QueryConstants;

public class QueryConstant {

    public static final String FIND_MATCHING_DRIVERS = "SELECT d.*, ST_Distance(d.current_location, :pickupLocation) AS distance " +
            "FROM driver d " +
            "WHERE d.available = true AND ST_DWithin(d.current_location, :pickupLocation, 10000) " +
            "ORDER BY distance " +
            "LIMIT 10";


    public static final String FIND_NEAREST_TOP_RATED_DRIVERS = "SELECT d.* " +
            "FROM driver d " +
            "WHERE d.available = true AND ST_DWithin(d.current_location, :pickupLocation, 15000) " +
            "ORDER BY d.rating DESC " +
            "LIMIT 10";

}
