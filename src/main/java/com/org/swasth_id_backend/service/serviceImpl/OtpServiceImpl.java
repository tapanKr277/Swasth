package com.org.swasth_id_backend.service.serviceImpl;

import com.org.swasth_id_backend.service.EmailService;
import com.org.swasth_id_backend.service.OtpService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    private final Random random = new Random();

    private EmailService emailService;

    @Override
    public String generateOtp() {
        int otp = random.nextInt(999999);
        return  String.format("%06d", otp);
    }

    @Override
    public boolean sendOTP(String email, String otp) {
        if(emailService.sendEmailOtp(email, otp)){
            return true;
        }
        return false;
    }
}
