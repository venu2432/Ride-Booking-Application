package com.project.ridebooking.RideBookingApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RideBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RideBookingApplication.class, args);
	}

}
