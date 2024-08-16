package com.project.ridebooking.RideBookingApplication.Config;

import com.project.ridebooking.RideBookingApplication.Dto.LocationDto;
import com.project.ridebooking.RideBookingApplication.Util.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(LocationDto.class, Point.class).setConverter(converter ->{
            LocationDto locationDto = converter.getSource();
            return GeometryUtil.createPoint(locationDto);
                });

        modelMapper.typeMap(Point.class, LocationDto.class).setConverter(convertor ->{
            Point point = convertor.getSource();
            double coordinates[] = {
                    point.getX(),
                    point.getY()
            };
            return new LocationDto(coordinates);
        });

        return modelMapper;
    }

}
