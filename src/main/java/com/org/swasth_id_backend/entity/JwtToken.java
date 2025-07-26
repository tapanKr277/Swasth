package com.org.swasth_id_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "jwt_token")
public class JwtToken extends BaseEntity{

    @Column(name = "token", nullable = false, length = 500)
    private String token;

    @Column(nullable = false, name = "user_id")
    private UUID userId;

    @Column(nullable = false, name = "expiration_time")
    private LocalDateTime expirationTime;

    @Column(nullable = false, name = "black_listed")
    private Boolean blacklisted = false;


}
