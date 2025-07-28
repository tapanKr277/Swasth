package com.org.swasth_id_backend.mapper;

import com.org.swasth_id_backend.dto.PasswordResetOtpDto;
import com.org.swasth_id_backend.entity.PasswordResetOtp;

public class PasswordResetOtpMapper {

    // Method to map PasswordResetOtpDto to PasswordResetOtp entity
    public static PasswordResetOtp passwordResetOtpDtoToPasswordResetOtp(PasswordResetOtpDto passwordResetOtpDto) {
        if (passwordResetOtpDto == null) {
            return null;
        }

        PasswordResetOtp passwordResetOtp = new PasswordResetOtp();
        passwordResetOtp.setId(passwordResetOtpDto.getId());
        passwordResetOtp.setEmail(passwordResetOtpDto.getEmail());
        passwordResetOtp.setOtp(passwordResetOtpDto.getOtp());
        passwordResetOtp.setExpiryTime(passwordResetOtpDto.getExpiryTime());
        passwordResetOtp.setCreatedAt(passwordResetOtpDto.getCreatedAt());
        passwordResetOtp.setLastUpdate(passwordResetOtpDto.getLastUpdate());

        return passwordResetOtp;
    }

    // Method to map PasswordResetOtp entity to PasswordResetOtpDto
    public static PasswordResetOtpDto passwordResetOtpToPasswordResetOtpDto(PasswordResetOtp passwordResetOtp) {
        if (passwordResetOtp == null) {
            return null;
        }

        PasswordResetOtpDto passwordResetOtpDto = new PasswordResetOtpDto();
        passwordResetOtpDto.setId(passwordResetOtp.getId());
        passwordResetOtpDto.setEmail(passwordResetOtp.getEmail());
        passwordResetOtpDto.setOtp(passwordResetOtp.getOtp());
        passwordResetOtpDto.setExpiryTime(passwordResetOtp.getExpiryTime());
        passwordResetOtpDto.setCreatedAt(passwordResetOtp.getCreatedAt());
        passwordResetOtpDto.setLastUpdate(passwordResetOtp.getLastUpdate());

        return passwordResetOtpDto;
    }
}
