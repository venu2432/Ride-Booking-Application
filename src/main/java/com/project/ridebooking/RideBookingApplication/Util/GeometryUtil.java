package com.project.ridebooking.RideBookingApplication.Util;

import com.project.ridebooking.RideBookingApplication.Dto.LocationDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class GeometryUtil {

    public static Point createPoint(LocationDto locationDto){
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coordinate = new Coordinate(locationDto.getCoordinates()[0],
                locationDto.getCoordinates()[1]);
        return geometryFactory.createPoint(coordinate);
    }

}
