package com.project.ridebooking.RideBookingApplication.Strategy;

import com.project.ridebooking.RideBookingApplication.Entity.Enums.WeatherCondition;

import java.util.Objects;

public class WeatherCategorizer {

    public static final Integer SUNNY = 1000;
    public static final Integer PARTLY_CLOUDY = 1003;
    public static final Integer CLOUDY = 1006;
    public static final Integer OVERCAST = 1009;
    public static final Integer MIST = 1030;
    public static final Integer PATCHY_RAIN_POSSIBLE = 1063;
    public static final Integer PATCHY_SNOW_POSSIBLE = 1066;
    public static final Integer PATCHY_SLEET_POSSIBLE = 1069;
    public static final Integer PATCHY_FREEZING_DRIZZLE_POSSIBLE = 1072;
    public static final Integer THUNDERY_OUTBREAKS_POSSIBLE = 1087;
    public static final Integer BLOWING_SNOW = 1114;
    public static final Integer BLIZZARD = 1117;
    public static final Integer FOG = 1135;
    public static final Integer FREEZING_FOG = 1147;
    public static final Integer PATCHY_LIGHT_DRIZZLE = 1150;
    public static final Integer LIGHT_DRIZZLE = 1153;
    public static final Integer FREEZING_DRIZZLE = 1168;
    public static final Integer HEAVY_FREEZING_DRIZZLE = 1171;
    public static final Integer PATCHY_LIGHT_RAIN = 1180;
    public static final Integer LIGHT_RAIN = 1183;
    public static final Integer MODERATE_RAIN_AT_TIMES = 1186;
    public static final Integer MODERATE_RAIN = 1189;
    public static final Integer HEAVY_RAIN_AT_TIMES = 1192;
    public static final Integer HEAVY_RAIN = 1195;
    public static final Integer LIGHT_FREEZING_RAIN = 1198;
    public static final Integer MODERATE_OR_HEAVY_FREEZING_RAIN = 1201;
    public static final Integer LIGHT_SLEET = 1204;
    public static final Integer MODERATE_OR_HEAVY_SLEET = 1207;
    public static final Integer PATCHY_LIGHT_SNOW = 1210;
    public static final Integer LIGHT_SNOW = 1213;
    public static final Integer PATCHY_MODERATE_SNOW = 1216;
    public static final Integer MODERATE_SNOW = 1219;
    public static final Integer PATCHY_HEAVY_SNOW = 1222;
    public static final Integer HEAVY_SNOW = 1225;
    public static final Integer ICE_PELLETS = 1237;
    public static final Integer LIGHT_RAIN_SHOWER = 1240;
    public static final Integer MODERATE_OR_HEAVY_RAIN_SHOWER = 1243;
    public static final Integer TORRENTIAL_RAIN_SHOWER = 1246;
    public static final Integer LIGHT_SLEET_SHOWERS = 1249;
    public static final Integer MODERATE_OR_HEAVY_SLEET_SHOWERS = 1252;
    public static final Integer LIGHT_SNOW_SHOWERS = 1255;
    public static final Integer MODERATE_OR_HEAVY_SNOW_SHOWERS = 1258;
    public static final Integer LIGHT_SHOWERS_OF_ICE_PELLETS = 1261;
    public static final Integer MODERATE_OR_HEAVY_SHOWERS_OF_ICE_PELLETS = 1264;
    public static final Integer PATCHY_LIGHT_RAIN_WITH_THUNDER = 1273;
    public static final Integer MODERATE_OR_HEAVY_RAIN_WITH_THUNDER = 1276;
    public static final Integer PATCHY_LIGHT_SNOW_WITH_THUNDER = 1279;
    public static final Integer MODERATE_OR_HEAVY_SNOW_WITH_THUNDER = 1282;


    public static WeatherCondition categorizeWeather(Integer weatherCode) {
        if (isGoodWeather(weatherCode)) {
            return WeatherCondition.GOOD;
        } else if (isAverageWeather(weatherCode)) {
            return WeatherCondition.AVERAGE;
        } else {
            return WeatherCondition.BAD;
        }
    }

    private static boolean isGoodWeather(Integer weatherCode) {
        // Good weather conditions for travel
        return Objects.equals(weatherCode, SUNNY) ||
                Objects.equals(weatherCode, PARTLY_CLOUDY) ||
                Objects.equals(weatherCode, CLOUDY);
    }

    private static boolean isAverageWeather(Integer weatherCode) {
        // Average weather conditions for travel
        return Objects.equals(weatherCode, OVERCAST) ||
                Objects.equals(weatherCode, MIST) ||
                Objects.equals(weatherCode, PATCHY_RAIN_POSSIBLE) ||
                Objects.equals(weatherCode, PATCHY_SNOW_POSSIBLE) ||
                Objects.equals(weatherCode, PATCHY_SLEET_POSSIBLE) ||
                Objects.equals(weatherCode, PATCHY_FREEZING_DRIZZLE_POSSIBLE) ||
                Objects.equals(weatherCode, THUNDERY_OUTBREAKS_POSSIBLE) ||
                Objects.equals(weatherCode, FOG) ||
                Objects.equals(weatherCode, PATCHY_LIGHT_DRIZZLE) ||
                Objects.equals(weatherCode, LIGHT_DRIZZLE) ||
                Objects.equals(weatherCode, PATCHY_LIGHT_RAIN) ||
                Objects.equals(weatherCode, LIGHT_RAIN) ||
                Objects.equals(weatherCode, LIGHT_FREEZING_RAIN) ||
                Objects.equals(weatherCode, LIGHT_SLEET) ||
                Objects.equals(weatherCode, PATCHY_LIGHT_SNOW) ||
                Objects.equals(weatherCode, LIGHT_SNOW) ||
                Objects.equals(weatherCode, LIGHT_RAIN_SHOWER) ||
                Objects.equals(weatherCode, LIGHT_SNOW_SHOWERS) ||
                Objects.equals(weatherCode, PATCHY_LIGHT_SNOW_WITH_THUNDER);
    }
}


