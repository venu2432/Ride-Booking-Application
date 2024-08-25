package com.project.ridebooking.RideBookingApplication.Controller;


import com.project.ridebooking.RideBookingApplication.Dto.*;
import com.project.ridebooking.RideBookingApplication.Service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rider")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;

    @PostMapping("/requestRide")
    public ResponseEntity<RideRequestDto> requestRide(@RequestBody RideRequestDto requestBody){
        return ResponseEntity.ok(riderService.requestRide(requestBody));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(riderService.cancelRide(rideId));
    }

    @PostMapping("/rateDriver")
    public ResponseEntity<DriverDto> rateDriver(@RequestBody RatingDto ratingDto){
        return ResponseEntity.ok(riderService.rateDriver(ratingDto.getRideId(), ratingDto.getRating()));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<RiderDto> getMyProfile(){
        return ResponseEntity.ok(riderService.getMyProfile());
    }

    @GetMapping("/getMyRides")
    public ResponseEntity<Page<RideDto>> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffset,
                                                       @RequestParam(defaultValue = "10", required = false) Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffset, pageSize,
                Sort.by(Sort.Direction.DESC,"createdTime"));
        return ResponseEntity.ok(riderService.getAllMyRides(pageRequest));
    }

    @PostMapping("/rateDriver/{rideId}/{rating}")
    public ResponseEntity<DriverDto> rateDriver(@PathVariable Long rideId, @PathVariable Integer rating){
        return ResponseEntity.ok(riderService.rateDriver(rideId, rating));
    }

}
