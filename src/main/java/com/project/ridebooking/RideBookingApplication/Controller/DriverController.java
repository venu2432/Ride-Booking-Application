package com.project.ridebooking.RideBookingApplication.Controller;

import com.project.ridebooking.RideBookingApplication.Dto.RideDto;
import com.project.ridebooking.RideBookingApplication.Entity.RideStartDto;
import com.project.ridebooking.RideBookingApplication.Service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId){

        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
    }

    @PostMapping("/startRide")
    public ResponseEntity<RideDto> startRide(@RequestBody RideStartDto rideStartDto){

        return ResponseEntity.ok(driverService.startRide(rideStartDto.getRideId(), rideStartDto.getOtp()));
    }

}
