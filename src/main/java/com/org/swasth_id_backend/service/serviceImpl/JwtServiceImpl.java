package com.org.swasth_id_backend.service.serviceImpl;

import com.org.swasth_id_backend.dto.UserDto;
import com.org.swasth_id_backend.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.access-token.expiration}")
	private long accessTokenExpiration;

	@Value("${jwt.refresh-token.expiration}")
	private long refreshTokenExpiration;


	@PostConstruct
	public void validateSecretKey() {
		if (secretKey == null || secretKey.isEmpty()) {
			throw new IllegalStateException("JWT secret key is not configured");
		}
		validateBase64EncodedKey(secretKey);
	}


	private void validateBase64EncodedKey(String key) {
		try {
			// Attempt to decode the key to check if it's valid Base64
			Base64.getDecoder().decode(key);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException("JWT secret key is not properly Base64-encoded", e);
		}
	}

	@Override
	public String generateToken(UserDto userDto) {
		Claims claims = Jwts.claims().setSubject(userDto.getUsername());
		List<String> roleNames = userDto.getRoles().stream()
				.map(role -> role.getRole())
				.collect(Collectors.toList());

		// Add the role names to the claims
		claims.put("roles", roleNames);
		claims.put("email", userDto.getEmail());
		claims.put("userId", userDto.getUserId());
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDto.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
				.signWith(getKey(), SignatureAlgorithm.HS256).compact();
	}

	@Override
	public String generateRefreshToken(UserDto userDto) {
		Claims claims = Jwts.claims().setSubject(userDto.getUsername());
		List<String> roleNames = userDto.getRoles().stream()
				.map(role -> role.getRole())
				.collect(Collectors.toList());

		// Add the role names to the claims
		claims.put("roles", roleNames);
		claims.put("email", userDto.getEmail());
		claims.put("username", userDto.getUsername());
		claims.put("userId", userDto.getUserId());
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDto.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
				.signWith(getKey(), SignatureAlgorithm.HS256).compact();
	}



	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public String extractUserName(String token) {
		return extractAllClaims(token).getSubject();
	}

	@Override
	public String extractEmail(String token){
		Claims claims = extractAllClaims(token);
		return (String) claims.get("email");
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	@Override
	public Claims getClaimsFromToken(String token) {
	    return extractAllClaims(token);
	}

	@Override
	public boolean validateToken(String token, UserDetails userDetails) {
		try {
			return userDetails.getUsername().equals(extractUserName(token)) && !isTokenExpired(token);
		} catch (Exception e) {
			log.error("Token validation failed: {}", e.getMessage());
			return false;
		}
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}

	@Override
	public List<String> extractRoles(String token) {
		Claims claims = Jwts.parserBuilder()
							.setSigningKey(getKey())
							.build()
							.parseClaimsJws(token)
							.getBody();
		return (List<String>) claims.get("roles");
	}
}
