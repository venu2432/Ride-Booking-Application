package com.project.ridebooking.RideBookingApplication;

import com.project.ridebooking.RideBookingApplication.Service.NotificationMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RideBookingApplicationTests {

	@Autowired
	private NotificationMailService notificationMailService;

	@Test
	void contextLoads() {
		notificationMailService.sendRideConfirmationEmail("venumadhavmaripi@gmail.com"
				,"Your ride is confirmed!!");
	}



}
