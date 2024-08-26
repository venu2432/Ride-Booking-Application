package com.project.ridebooking.RideBookingApplication.Service;

import com.project.ridebooking.RideBookingApplication.Dto.DriverDto;
import com.project.ridebooking.RideBookingApplication.Dto.SignUpDto;
import com.project.ridebooking.RideBookingApplication.Dto.UserDto;

public interface AuthService {

    String[] login(String email, String password);

    UserDto singUp(SignUpDto signUpDto);

    DriverDto onboardNewDriver(Long userId,String vehicleId);
}
