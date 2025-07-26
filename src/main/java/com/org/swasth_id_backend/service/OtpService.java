package com.org.swasth_id_backend.service;


public interface OtpService {

    public String generateOtp();

    public boolean sendOTP(String email, String otp);
}
