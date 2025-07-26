package com.org.swasth_id_backend.service;

import com.org.swasth_id_backend.dto.UserDto;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface JwtService {

	public String generateToken(UserDto userDto);

	public String generateRefreshToken(UserDto userDto);

	public String extractUserName(String token);

	public String extractEmail(String token);

	public boolean validateToken(String token, UserDetails userDetails);

	public Claims getClaimsFromToken(String refreshToken);

	public List<String> extractRoles(String token);
}
