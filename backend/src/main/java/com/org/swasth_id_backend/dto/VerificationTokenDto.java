package com.org.swasth_id_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationTokenDto {

    private UUID verificationTokenId;
    private String email;
    private String token;
    private LocalDateTime expiryTime;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;

}