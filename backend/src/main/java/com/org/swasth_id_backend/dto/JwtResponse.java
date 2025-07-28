package com.org.swasth_id_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtResponse {

    private String accessToken;
    private String refreshToken;
    private String email;
    private UUID userId;
    private String username;
}
