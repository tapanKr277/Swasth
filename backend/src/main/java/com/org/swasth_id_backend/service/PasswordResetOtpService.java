package com.org.swasth_id_backend.service;


import com.org.swasth_id_backend.dto.PasswordResetOtpDto;
import com.org.swasth_id_backend.entity.PasswordResetOtp;
import com.org.swasth_id_backend.exception.InvalidOtpException;

import java.util.List;

public interface PasswordResetOtpService {

    public PasswordResetOtp createOtp(String email);

    public boolean validateOtp(String email, String otp) throws InvalidOtpException;

    public void deleteOtpByEmail(String email);

    public void deleteOtpByOtp(String otp);

    List<PasswordResetOtpDto> getAllOtpList();
}
