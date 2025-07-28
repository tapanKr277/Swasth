package com.org.swasth_id_backend.service;

import com.org.swasth_id_backend.dto.EmailDetails;

public interface EmailService {

    public String sendSimpleMail(com.org.swasth_id_backend.dto.EmailDetails details);

    public String sendMailWithAttachment(EmailDetails details);

    public boolean sendEmailOtp(String email, String otp);

    public boolean sendVerificationEmail(String email, String token);
}
