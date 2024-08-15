package com.project.ridebooking.RideBookingApplication.Dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationDto {

    private double[] coordinates;

    private String type = "Point";

    public LocationDto(double [] coordinates){
        this.coordinates = coordinates;
    }

}
