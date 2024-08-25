package com.project.ridebooking.RideBookingApplication.Service.Impl;

import com.project.ridebooking.RideBookingApplication.Dto.DriverDto;
import com.project.ridebooking.RideBookingApplication.Dto.SignUpDto;
import com.project.ridebooking.RideBookingApplication.Dto.UserDto;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.Role;
import com.project.ridebooking.RideBookingApplication.Entity.User;
import com.project.ridebooking.RideBookingApplication.Exception.RunTimeConflictException;
import com.project.ridebooking.RideBookingApplication.Repository.RiderRepository;
import com.project.ridebooking.RideBookingApplication.Repository.UserRepository;
import com.project.ridebooking.RideBookingApplication.Service.AuthService;
import com.project.ridebooking.RideBookingApplication.Service.RiderService;
import com.project.ridebooking.RideBookingApplication.Service.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final RiderService riderService;
    private final RiderRepository riderRepository;
    private final WalletService walletService;

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    @Transactional
    public UserDto singUp(SignUpDto signUpDto) {
        User exisitingUser = userRepository.findByEmail(signUpDto.getEmail()).orElse(null);
        if(exisitingUser != null){
            throw new RunTimeConflictException("Cannot signup, User with " + signUpDto.getEmail() + " already exists");
        }
        User user = modelMapper.map(signUpDto, User.class);
        user.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepository.save(user);

        riderService.createNewRide(savedUser);
        walletService.createNewWallet(savedUser);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId) {
        return null;
    }
}
