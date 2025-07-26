package com.org.swasth_id_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity{

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Doctor doctor;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Patient patient;

    @Column(name = "first_name", length = 30)
    private String firstName;

    @Column(name = "last_name", length = 30)
    private String lastName;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer age;

    @Column(name = "blood_group", length = 5)
    private String bloodGroup;

    @Column(name="email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "phone_number", nullable = true, unique = true, length = 10)
    private String phoneNumber;

    @Column(name = "address", length = 255)
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE", nullable = false)
    private Boolean isActive = true;

    @Column(name = "is_verified", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private Boolean isVerified = false;

    @Column(name = "is_otp_verified", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private Boolean isOtpVerified = false;

    @Column(name = "otp_last_update", nullable = true)
    private LocalDateTime otpLastUpdate;



}