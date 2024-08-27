package com.project.ridebooking.RideBookingApplication.Service.Impl;

import com.project.ridebooking.RideBookingApplication.Dto.DriverDto;
import com.project.ridebooking.RideBookingApplication.Dto.SignUpDto;
import com.project.ridebooking.RideBookingApplication.Dto.UserDto;
import com.project.ridebooking.RideBookingApplication.Entity.Driver;
import com.project.ridebooking.RideBookingApplication.Entity.Enums.Role;
import com.project.ridebooking.RideBookingApplication.Entity.User;
import com.project.ridebooking.RideBookingApplication.Exception.ResourceNotFoundException;
import com.project.ridebooking.RideBookingApplication.Exception.RunTimeConflictException;
import com.project.ridebooking.RideBookingApplication.Repository.UserRepository;
import com.project.ridebooking.RideBookingApplication.Security.JWTService;
import com.project.ridebooking.RideBookingApplication.Service.AuthService;
import com.project.ridebooking.RideBookingApplication.Service.DriverService;
import com.project.ridebooking.RideBookingApplication.Service.RiderService;
import com.project.ridebooking.RideBookingApplication.Service.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public String[] login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));


        User user = (User)  authentication.getPrincipal();

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new String[]{accessToken, refreshToken};
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        riderService.createNewRide(savedUser);
        walletService.createNewWallet(savedUser);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId, String vehicleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new ResourceNotFoundException("User not found with user id: "+userId);
        });

        if(user.getRoles().contains(Role.DRIVER))
            throw new RunTimeConflictException("User is already a driver");

        Driver driver = Driver.builder()
                .user(user)
                .rating(0.0)
                .available(true)
                .vehicleId(vehicleId)
                .build();
        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);
        Driver createdDriver = driverService.createNewDriver(driver);
        return modelMapper.map(createdDriver, DriverDto.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with user id: "+userId)
        );
        return jwtService.generateRefreshToken(user);
    }
}
