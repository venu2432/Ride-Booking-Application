package com.project.ridebooking.RideBookingApplication.Dto;

import com.project.ridebooking.RideBookingApplication.Entity.Driver;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.PaymentMethod;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.RideStatus;
import com.project.ridebooking.RideBookingApplication.Entity.Rider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideDto {

    private Long id;

    private Point pickUpLocation;

    private Point dropOffLocation;

    private LocalDateTime createdTime;

    private RiderDto rider;

    private DriverDto driver;

    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;

    private Double fare;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
