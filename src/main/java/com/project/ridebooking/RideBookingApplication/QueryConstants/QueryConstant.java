package com.project.ridebooking.RideBookingApplication.QueryConstants;

public class QueryConstant {

    public static final String FIND_MATCHING_DRIVERS = "SELECT d*, ST_Distance(d.current_location, :pickupLocation) as distance "+
            "from DRIVERS AS D WHERE AVAILABLE = true AND ST_DWithin(d.current_lcoation, :pickupLocation, 10000) "+
            "ORDER BY distance limit 10";


//    public static final String FIND_NEAREST_TOP_RATED_DRIVERS = "SELECT D.*, ST_Distance(:pickupLocation) as distance FROM DRIVERS D\n" +
//            "WHERE ST_Within(D.current_location, :pickupLocation, 100)\n" +
//            "AND D.available = true\n" +
//            "ORDER BY RATING,ST_Distance(:pickupLocation) LIMIT 10";

}
