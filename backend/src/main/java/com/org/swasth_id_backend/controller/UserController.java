package com.org.swasth_id_backend.controller;


import com.org.swasth_id_backend.dto.UserDto;
import com.org.swasth_id_backend.exception.ResourceNotFoundException;
import com.org.swasth_id_backend.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/api/auth/user/")
@Tag(name = "User Controller", description = "User controller for creating user")
@Slf4j
public class UserController {


    @Autowired
    private UserService userService;


	@GetMapping("/")
	public String test(){
		return "Hello world";
	}

	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@GetMapping("/get-user-profile")
	public ResponseEntity<UserDto> getUserByUserName(@RequestParam String username) throws ResourceNotFoundException {
		UserDto userDto = null;
		try{
			userDto = userService.getUserDtoByUserName(username);
		}
		catch (Exception e){
			throw new ResourceNotFoundException("User not found, error is "+ e.getMessage());
		}

        return ResponseEntity.ok(userDto);
	}


	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@GetMapping("/get-user-id")
	public ResponseEntity<UserDto> getUserById(@RequestParam(name = "user_id") UUID userId){
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
	}







}
