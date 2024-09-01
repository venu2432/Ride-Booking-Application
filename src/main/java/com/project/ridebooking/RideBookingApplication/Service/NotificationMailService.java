package com.project.ridebooking.RideBookingApplication.Service;

import com.project.ridebooking.RideBookingApplication.Entity.Ride;

public interface NotificationMailService {

    public void sendMail(String email, String subject, String body);

    public void sendEmail(String[] emails,String subject, String body);

    public void sendConfirmationMail(Ride createdRide);
}
