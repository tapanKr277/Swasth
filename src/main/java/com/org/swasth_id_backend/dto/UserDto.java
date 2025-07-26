package com.org.swasth_id_backend.dto;

import com.org.swasth_id_backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private UUID userId;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private Boolean isActive;
    private Boolean isVerified;
    private Boolean isOtpVerified;
    private LocalDateTime otpLastUpdate;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;
    private Set<Role> roles;

}
