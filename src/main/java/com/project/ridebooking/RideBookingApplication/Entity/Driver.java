package com.project.ridebooking.RideBookingApplication.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_driver_vehicleId", columnList = "vehicleId")
})
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double rating;

    private Boolean available;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point currentLocation;

    private String vehicleId;

}
