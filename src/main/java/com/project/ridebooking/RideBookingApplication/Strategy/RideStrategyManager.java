package com.project.ridebooking.RideBookingApplication.Strategy;

import com.project.ridebooking.RideBookingApplication.Strategy.Impl.DriverMatchingHighestRatedDriverStrategy;
import com.project.ridebooking.RideBookingApplication.Strategy.Impl.DriverMatchingNearestDriverStrategy;
import com.project.ridebooking.RideBookingApplication.Strategy.Impl.RideFareDefaultCalculationStrategy;
import com.project.ridebooking.RideBookingApplication.Strategy.Impl.RideFareSurgePricingCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
    private final DriverMatchingHighestRatedDriverStrategy highestRatedDriverStrategy;
    private final RideFareDefaultCalculationStrategy defaultCalculationStrategy;
    private final RideFareSurgePricingCalculationStrategy surgePricingCalculationStrategy;


    public DriverMatchingStrategy driverMatchingStrategy(Double riderRating){
        if(riderRating > 4.0){
            return highestRatedDriverStrategy;
        }
        return nearestDriverStrategy;
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(){

        LocalTime surgeStartTime = LocalTime.of(18,0);
        LocalTime surgeEndTime = LocalTime.of(21,0);
        LocalTime currentTime = LocalTime.now();
        boolean isSurgeTime = currentTime.isAfter(surgeStartTime)  && currentTime.isBefore(surgeEndTime);
        if(isSurgeTime){
            return surgePricingCalculationStrategy;
        }
        return defaultCalculationStrategy;
    }


}
