package com.project.ridebooking.RideBookingApplication.Service.Impl;

import com.project.ridebooking.RideBookingApplication.Dto.DriverDto;
import com.project.ridebooking.RideBookingApplication.Dto.SignUpDto;
import com.project.ridebooking.RideBookingApplication.Dto.UserDto;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.Role;
import com.project.ridebooking.RideBookingApplication.Entity.Rider;
import com.project.ridebooking.RideBookingApplication.Entity.User;
import com.project.ridebooking.RideBookingApplication.Exception.RunTimeConflictException;
import com.project.ridebooking.RideBookingApplication.Repository.RiderRepository;
import com.project.ridebooking.RideBookingApplication.Repository.UserRepository;
import com.project.ridebooking.RideBookingApplication.Service.AuthService;
import com.project.ridebooking.RideBookingApplication.Service.RiderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final RiderService riderService;
    private final RiderRepository riderRepository;

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public UserDto singUp(SignUpDto signUpDto) {
        User exisitingUser = userRepository.findByEmail(signUpDto.getEmail()).orElseThrow(() ->
                new RunTimeConflictException("Cannot signup, User with " + signUpDto.getEmail() + " already exists"));

        User user = modelMapper.map(signUpDto, User.class);
        user.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepository.save(user);

        riderService.createNewRide(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId) {
        return null;
    }
}
