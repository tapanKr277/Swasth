package com.org.swasth_id_backend.service;


import com.org.swasth_id_backend.dto.UserDto;
import com.org.swasth_id_backend.entity.JwtToken;

import java.util.UUID;

public interface JwtTokenService {

    public String generateToken(UserDto userDto);


    public JwtToken getJwtTokenByUserIdAndBlacklisted(UUID userId, Boolean blackListed);

    JwtToken getJwtTokenByToken(String token);

    JwtToken saveToken(JwtToken jwtToken);

    String generateRefreshToken(UserDto userDto);
}
