package com.project.ridebooking.RideBookingApplication.Controller;

import com.project.ridebooking.RideBookingApplication.Dto.SignUpDto;
import com.project.ridebooking.RideBookingApplication.Dto.UserDto;
import com.project.ridebooking.RideBookingApplication.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    UserDto signUp(@RequestBody SignUpDto signupDto) {
        return authService.singUp(signupDto);
    }

}
