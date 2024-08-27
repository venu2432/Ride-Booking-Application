package com.project.ridebooking.RideBookingApplication.Controller;

import com.project.ridebooking.RideBookingApplication.Dto.DriverDto;
import com.project.ridebooking.RideBookingApplication.Dto.RatingDto;
import com.project.ridebooking.RideBookingApplication.Dto.RideDto;
import com.project.ridebooking.RideBookingApplication.Dto.RiderDto;
import com.project.ridebooking.RideBookingApplication.Entity.RideStartDto;
import com.project.ridebooking.RideBookingApplication.Service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
@Secured("ROLE_DRIVER")
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

    @PostMapping("/endRide/{rideId}")
    public ResponseEntity<RideDto> endRide(@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.endRide(rideId));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.cancelRide(rideId));
    }

    @PostMapping("/rateRider")
    public ResponseEntity<RiderDto> rateDriver(@RequestBody RatingDto ratingDto){
        return ResponseEntity.ok(driverService.rateRider(ratingDto.getRideId(), ratingDto.getRating()));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<DriverDto> getMyProfile(){
        return ResponseEntity.ok(driverService.getMyProfile());
    }

    @GetMapping("/getMyRides")
    public ResponseEntity<Page<RideDto>> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffset,
                                                       @RequestParam(defaultValue = "10", required = false) Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffset,pageSize,
                Sort.by(Sort.Direction.DESC,"createdTime"));
        return ResponseEntity.ok(driverService.getAllMyRides(pageRequest));
    }

    @PostMapping("/rateRider/{rideId}/{rating}")
    public ResponseEntity<RiderDto> rateRider(@PathVariable Long rideId, @PathVariable Integer rating){
        return ResponseEntity.ok(driverService.rateRider(rideId, rating));
    }

}
