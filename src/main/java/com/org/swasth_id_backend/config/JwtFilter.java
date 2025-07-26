package com.org.swasth_id_backend.config;

import java.io.IOException;


import com.org.swasth_id_backend.entity.JwtToken;
import com.org.swasth_id_backend.service.JwtService;
import com.org.swasth_id_backend.service.JwtTokenService;
import com.org.swasth_id_backend.service.serviceImpl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;

	@Autowired
    private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtTokenService jwtTokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {


		String authHeader = request.getHeader("Authorization");
//		String username = null;
		String token = null;
		String email = null;

		// Extract JWT token from Authorization header
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7); // Remove "Bearer " prefix
			JwtToken jwtToken = jwtTokenService.getJwtTokenByToken(token);
			if(jwtToken==null){
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted");
				return;
			}
			if (jwtToken != null && jwtToken.getBlacklisted()) {
				// Token is blacklisted, prevent further processing (unauthorized)
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted");
				return;
			}
			try {
				// Extract username from the token
				email = jwtService.extractEmail(token);
			} catch (Exception e) {
				// Log the error and handle the exception as needed
				System.out.println("JWT extraction error: " + e.getMessage());
			}
		}

		// Validate token and set authentication context
		if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);

			if(jwtService.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

				// Set additional details for the authentication token (optional)
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Set the authentication in the SecurityContext
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		// Proceed with the filter chain
		filterChain.doFilter(request, response);
	}


}
