package com.org.swasth_id_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtFilter jwtFilter;

	@Autowired
	@Lazy
	private OauthSuccessHandler oauthSuccessHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	

	http.csrf(csrf -> csrf.disable());

		http.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
						.requestMatchers(
								"/api/auth/login",
								"/api/auth/add-user",
								"/api/auth/contact-us",
								"/api/auth/validate-token",
								"/api/auth/forgot-password",
								"/api/auth/verify-otp",
								"/api/auth/resend-otp",
								"/api/auth/change-password",
								"/api/auth/email/sendMail",
								"/api/auth/verify-email",
								"/oauth2/**",
								"/login/**",
								"/logout/**",
								"/swagger-ui/index.html",
								"/v3/api-docs/**",
								"/swagger-ui/**"
						).permitAll()
						.requestMatchers("/api/auth/admin/**").hasRole("ADMIN")
						.requestMatchers("/api/auth/user/**").hasAnyRole("USER", "ADMIN")
						
						.anyRequest().authenticated())
						.oauth2Login(oauth->{
							oauth.successHandler(oauthSuccessHandler);
						});
		
	
		http.httpBasic();

		http.headers(headers -> headers.xssProtection(xss -> xss.and())
									.contentTypeOptions(contentType -> contentType.disable())
									.frameOptions(frame -> frame.sameOrigin()));

		// http.requiresChannel(channel -> channel.anyRequest().requiresSecure());
		
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}




	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:4200"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "password"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
