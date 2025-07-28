package com.org.swasth_id_backend.controller;


import com.org.swasth_id_backend.dto.PasswordResetOtpDto;
import com.org.swasth_id_backend.dto.RoleDto;
import com.org.swasth_id_backend.dto.UserDto;
import com.org.swasth_id_backend.dto.VerificationTokenDto;
import com.org.swasth_id_backend.entity.User;
import com.org.swasth_id_backend.exception.ResourceNotFoundException;
import com.org.swasth_id_backend.exception.UserNotFoundException;
import com.org.swasth_id_backend.mapper.UserMapper;
import com.org.swasth_id_backend.service.PasswordResetOtpService;
import com.org.swasth_id_backend.service.RoleService;
import com.org.swasth_id_backend.service.UserService;
import com.org.swasth_id_backend.service.VerificationTokenService;
import com.org.swasth_id_backend.service.serviceImpl.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/admin")
@Tag(name = "Admin Controller", description = "Admin Controller to perform Admin operation")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    private final UserService userService;
    private final RoleService roleService;
    private final VerificationTokenService verificationTokenService;
    private final PasswordResetOtpService passwordResetOtpService;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AdminController(UserService userService,
                           RoleService roleService,
                           VerificationTokenService verificationTokenService,
                           PasswordResetOtpService passwordResetOtpService,
                           UserDetailsServiceImpl userDetailsService) {
        this.userService = userService;
        this.roleService = roleService;
        this.verificationTokenService = verificationTokenService;
        this.passwordResetOtpService = passwordResetOtpService;
        this.userDetailsService = userDetailsService;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all-user-list")
    public ResponseEntity<List<UserDto>> getAllUserList(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUserList());
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all-role-list")
    public ResponseEntity<List<RoleDto>> getAllRoleList(){
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAllRoles());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all-token-list")
    public ResponseEntity<List<VerificationTokenDto>> getAllVerificationTokenList(){
        return ResponseEntity.status(HttpStatus.OK).body(verificationTokenService.getAllTokenList());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all-otp-list")
    public ResponseEntity<List<PasswordResetOtpDto>> getAllOtpList(){
        return ResponseEntity.status(HttpStatus.OK).body(passwordResetOtpService.getAllOtpList());
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/update-user")
    public ResponseEntity<UserDto> updateUserData(@RequestBody UserDto userDto) throws UserNotFoundException {
        System.out.println(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserData(userDto));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create-user")
    @Transactional
    public ResponseEntity<Map<String, String>> createUser(@RequestBody UserDto userDto) throws ResourceNotFoundException {
        User user = UserMapper.userDtoToUser(userDto);
        userDetailsService.addUser(user);
        UserMapper.userToUserDto(user);
        Map<String, String> map = new HashMap<>();
        map.put("success", "User created successfully");
        return ResponseEntity.created(URI.create("/created")).body(map);
    }

}
