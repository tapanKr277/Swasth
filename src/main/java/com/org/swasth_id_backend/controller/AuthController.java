package com.org.swasth_id_backend.controller;

import com.org.swasth_id_backend.dto.*;
import com.org.swasth_id_backend.entity.JwtToken;
import com.org.swasth_id_backend.entity.PasswordResetOtp;
import com.org.swasth_id_backend.entity.User;
import com.org.swasth_id_backend.entity.VerificationToken;
import com.org.swasth_id_backend.exception.*;
import com.org.swasth_id_backend.mapper.UserMapper;
import com.org.swasth_id_backend.service.*;
import com.org.swasth_id_backend.service.serviceImpl.UserDetailsServiceImpl;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {


    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private PasswordResetOtpService passwordResetOtpService;

    @Autowired
    private JwtTokenService jwtTokenService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDto loginDto) throws ResourceNotFoundException, UserNotFoundException {

        UserDto userDto = userService.getUserByEmail(loginDto.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        if(!userDto.getIsVerified()){
            resendVerificationEmail(userDto.getEmail());
            throw new RuntimeException("Your account is not verified please verify your account a verification link forwarded to your registered mail");
        }
        String token = jwtTokenService.generateToken(userDto);
        String refreshToken = jwtTokenService.generateRefreshToken(userDto);
        return ResponseEntity.ok(new JwtResponse(token, refreshToken, userDto.getEmail(), userDto.getUserId(), userDto.getUsername()));

    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequest) throws UserNotFoundException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(refreshTokenRequest.getEmail());
        UserDto userDto = userService.getUserByEmail(refreshTokenRequest.getEmail());
        JwtToken jwtToken;
            try {
                if (!jwtService.validateToken(refreshTokenRequest.getRefreshToken(), userDetails)) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
                }
                jwtToken = jwtTokenService.getJwtTokenByToken(refreshTokenRequest.getRefreshToken());
                if (jwtToken == null || jwtToken.getBlacklisted()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is already used or invalid");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
            }

            String newAccessToken = jwtTokenService.generateToken(userDto);
            String newRefreshToken = jwtTokenService.generateRefreshToken(userDto);

        return ResponseEntity.ok(new JwtResponse(newAccessToken, newRefreshToken, userDto.getEmail(), userDto.getUserId(), userDto.getUsername()));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Map<String, String>> validateToken(@RequestBody TokenDto tokenDto){
        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenDto.getEmail());
        Map<String, String> response = new HashMap<>();
        try{
            jwtService.validateToken(tokenDto.getToken(), userDetails);
            response.put("success", "true");
            return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            response.put("error", "Invalid Token "+ e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @PostMapping("/add-user")
    public ResponseEntity<Map<String, String>> addUser(@RequestBody UserDto userDto) throws ResourceNotFoundException {
        Map<String, String> response = new HashMap<>();
            UserDto existingUser = null;
            try{
                existingUser = userService.getUserByEmail(userDto.getEmail());
                if(!existingUser.getIsVerified()){
                    verificationTokenService.resendVerificationToken(userDto.getEmail());
                    response.put("Success", "new Verification mail forwarded to your email");
                    return ResponseEntity.ok(response);
                }

                throw new RuntimeException("User with this email already Registered");
            }
            catch (Exception e){
                    User user = UserMapper.userDtoToUser(userDto);
                    userDetailsService.addUser(user);

                verificationTokenService.resendVerificationToken(userDto.getEmail());
                    response.put("success", "Account Verification mail forwarded to your email please verify your account");
                    return ResponseEntity.ok(response);
                }
    }

    @PostMapping("/contact-us")
    public ResponseEntity<Map<String, String>> contactUs(@RequestBody ContactUsDto contactUsDto){
        Map<String, String> res = new HashMap<>();
        res.put("success", "Message send successfully");
        return ResponseEntity.ok(res);
    }


    @PostMapping("/change-password-by-old-password")
    public ResponseEntity<Map<String, String>> changePasswordByOldPassword(@RequestBody ChangePasswordDto changePasswordDto, @RequestParam String username) throws WrongPasswordException, SamePasswordException, PasswordMisMatchException, UserNotFoundException {
        Map<String, String> response = new HashMap<>();
        UserDto user = userService.getUserByEmail(changePasswordDto.getEmail());
        PasswordResetOtp passwordResetOtp= passwordResetOtpService.createOtp(changePasswordDto.getEmail());
        emailService.sendEmailOtp(changePasswordDto.getEmail(), passwordResetOtp.getOtp());
        response.put("success", "check your email for OTP");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody ChangePasswordDto changePasswordDto) throws UserNotFoundException, PasswordMisMatchException {
        Map<String, String> response = new HashMap<>();
        UserDto user = userService.getUserByEmail(changePasswordDto.getEmail());
        if(!user.getIsVerified()){
            throw new UserNotVerifiedException("first verify your account");
        }
        if(user.getIsOtpVerified() && Duration.between(user.getOtpLastUpdate(), LocalDateTime.now()).toMinutes() <= 3){
            userDetailsService.changePassword(changePasswordDto);
            userDetailsService.updateIsOtpVerified(changePasswordDto.getEmail(), false);
            response.put("success", "Password changed successfully");
        }
        else{
            userDetailsService.updateIsOtpVerified(changePasswordDto.getEmail(), false);
            throw new RuntimeException("You are not allowed to changed password OTP verification is not done Please change your password via OTP or  By Old Password");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestParam String email) throws UserNotFoundException {
        Map<String, String> response = new HashMap<>();
        UserDto user = userService.getUserByEmail(email);
        if(!user.getIsVerified()){
            throw new UserNotVerifiedException("first verify your account");
        }
        userDetailsService.updateIsOtpVerified(email,false);
        passwordResetOtpService.deleteOtpByEmail(email);
        PasswordResetOtp otp = passwordResetOtpService.createOtp(email);
        emailService.sendEmailOtp(email, otp.getOtp());
        response.put("success", "check your email for OTP");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp")
    @Transactional
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody VerifyOtpDto verifyOtpDto) throws InvalidOtpException, OtpNotFound, UserNotFoundException {
        Map<String, String> response = new HashMap<>();
        UserDto user = userService.getUserByEmail(verifyOtpDto.getEmail());
        if(!user.getIsVerified()){
            throw new UserNotVerifiedException("first verify your account");
        }
        if(!user.getIsOtpVerified()){
            passwordResetOtpService.validateOtp(verifyOtpDto.getEmail(), verifyOtpDto.getOtp());
            userDetailsService.updateIsOtpVerified(verifyOtpDto.getEmail(), true);
            userDetailsService.updateOtpLastUpdate(verifyOtpDto.getEmail());
            passwordResetOtpService.deleteOtpByEmail(verifyOtpDto.getEmail());
            response.put("success", "OTP verified successfully. You can now reset your password.");
        }else{
            response.put("error", "User Otp is already verified please change your password");
        }
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public UserDetails test(@AuthenticationPrincipal UserDetails currentUser){
        return currentUser;
    }

    @GetMapping("/verify-email")
    public ResponseEntity<Map<String, String>> verifyEmail(@RequestParam String token, @RequestParam String email){
        Map<String, String> response = new HashMap<>();
        try {
            try{
                UserDto userDto = userService.getUserByEmail(email);
                if(!userDto.getIsVerified()){
                    verificationTokenService.validateVerificationToken(token, email);
                    userDetailsService.updateUserIsVerified(email, true);
                    verificationTokenService.deleteVerificationToken(email);
                    response.put("success","Email verified successfully." );
                    return ResponseEntity.ok(response);
                }
                else{
                    throw new RuntimeException("User already verified");
                }
            }catch (UserNotFoundException e) {
                throw new UsernameNotFoundException(e.getMessage());
            }
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping("/resend-verification-email")
    public ResponseEntity<String> resendVerificationEmail(@RequestParam String email) throws UserNotFoundException {
        try{
            UserDto userDto = userService.getUserByEmail(email);
            if(!userDto.getIsVerified()){
                try {
                    verificationTokenService.deleteVerificationToken(email);
                    VerificationToken verificationToken = verificationTokenService.resendVerificationToken(email);
                    emailService.sendVerificationEmail(email, verificationToken.getToken());
                    return ResponseEntity.ok("A new verification email has been sent. Please check your inbox.");
                } catch (RuntimeException e) {
                    return ResponseEntity.status(400).body(e.getMessage());
                }
            }
            else{
                return ResponseEntity.ok("User is already verified");
            }
        }
        catch (UserNotFoundException e){
            throw new UserNotFoundException(e.getMessage());
        }
    }

    @GetMapping("/resend-otp")
    public ResponseEntity<Map<String, String>> resendOtp(@RequestParam String email) throws UserNotFoundException {
        UserDto userDto = userService.getUserByEmail(email);
        if(!userDto.getIsVerified()){
            throw new UserNotVerifiedException("first verify your account");
        }
        Map<String, String>  response = new HashMap<>();
        if(!userDto.getIsOtpVerified()){
            passwordResetOtpService.deleteOtpByEmail(email);
            PasswordResetOtp passwordResetOtp = passwordResetOtpService.createOtp(email);
            emailService.sendEmailOtp(email, passwordResetOtp.getOtp());
            response.put("success", "New Otp send to your email");
            return ResponseEntity.ok(response);
        }
        else{
            throw new RuntimeException("Otp is Already verified");
        }
    }
}
