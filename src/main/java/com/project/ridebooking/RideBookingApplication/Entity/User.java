package com.project.ridebooking.RideBookingApplication.Entity;

import com.project.ridebooking.RideBookingApplication.Entity.Enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "APP_USER",
indexes = {
        @Index(name = "idx_user_email", columnList = "email")
})
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
