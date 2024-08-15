package com.project.ridebooking.RideBookingApplication.Exception;

public class RunTimeConflictException extends RuntimeException{
    public RunTimeConflictException() {
    }

    public RunTimeConflictException(String message) {
        super(message);
    }
}
