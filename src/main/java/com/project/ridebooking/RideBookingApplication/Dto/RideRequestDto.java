package com.project.ridebooking.RideBookingApplication.Dto;

import com.project.ridebooking.RideBookingApplication.Entity.Enums.PaymentMethod;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {

    private Long id;

    private LocationDto pickUpLocation;

    private LocationDto dropOffLocation;

    private LocalDateTime requestedTime;

    private RiderDto rider;

    private PaymentMethod paymentMethod;

    private RideRequestStatus rideRequestStatus;

}
