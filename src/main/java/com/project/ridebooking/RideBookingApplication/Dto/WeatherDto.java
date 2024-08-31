package com.project.ridebooking.RideBookingApplication.Dto;

import lombok.Data;

@Data
public class WeatherDto {
    private Location location;
    private Current current;

    @Data
    public static class Location {
        private String name;
        private String region;
        private String country;
        private Double lat;
        private Double lon;
        private String tz_id;
        private String localtime;
    }

    @Data
    public static class Current {
        private Double temp_c;
        private Double temp_f;
        private Integer is_day;
        private Condition condition;
        private Double wind_kph;
        private Integer humidity;
        private Double feelslike_c;
        private Double feelslike_f;
    }

    @Data
    public static class Condition {
        private String text;
        private String icon;
        private Integer code;
    }
}

