package com.project.ridebooking.RideBookingApplication.Strategy;

import com.project.ridebooking.RideBookingApplication.Entity.Payment;

public interface PaymentStrategy {

    static final Double PLATFORM_COMMISION = 0.3;

    void processPayment(Payment payment);
}
