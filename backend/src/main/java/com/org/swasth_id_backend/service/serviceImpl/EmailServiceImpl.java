package com.org.swasth_id_backend.service.serviceImpl;

import com.org.swasth_id_backend.dto.EmailDetails;
import com.org.swasth_id_backend.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public String sendSimpleMail(EmailDetails details) {
        String subject = "New Contact Us Message from " + details.getSenderName();
        String text = generateContactUsEmailContent(details);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(details.getSenderEmail());
            mimeMessageHelper.setTo("tapankr277@gmail.com");
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text, true);

            mailSender.send(mimeMessage);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    private String generateContactUsEmailContent(EmailDetails details) {
        return "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<title>Contact Us Message</title>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; color: #333; background-color: #f4f7fc; margin: 0; padding: 0; }" +
                ".email-container { width: 100%; max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); }" +
                ".email-header { text-align: center; margin-bottom: 20px; }" +
                ".email-header h1 { color: #4A90E2; font-size: 24px; }" +
                ".email-body { font-size: 16px; line-height: 1.5; padding: 20px; text-align: center; }" +
                ".email-footer { text-align: center; font-size: 12px; color: #aaa; margin-top: 20px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='email-container'>" +
                "<div class='email-header'>" +
                "<h1>New Message from " + details.getSenderName() + "</h1>" +
                "</div>" +
                "<div class='email-body'>" +
                "<p><strong>Name:</strong> " + details.getSenderName() + "</p>" +
                "<p><strong>Email:</strong> " + details.getSenderEmail() + "</p>" +
                "<p><strong>Message:</strong></p>" +
                "<p>" + details.getMessage() + "</p>" +
                "</div>" +
                "<div class='email-footer'>" +
                "<p>&copy; 2024 Gyanpath. All rights reserved.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    @Override
    public String sendMailWithAttachment(EmailDetails details) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(details.getSenderEmail());
            mimeMessageHelper.setTo("tapankr277@gmail.com");
            mimeMessageHelper.setSubject("New Contact Us Message from " + details.getSenderName());
            mimeMessageHelper.setText(generateContactUsEmailContent(details), true);

            // Adding the attachment
            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
            mimeMessageHelper.addAttachment(file.getFilename(), file);

            // Sending the mail
            mailSender.send(mimeMessage);
            return "Mail sent Successfully";
        } catch (MessagingException e) {
            return "Error while sending mail!!!";
        }
    }

    @Override
    public boolean sendEmailOtp(String email, String otp) {
        String subject = "Your OTP for Password Change";
        String text = generateOtpEmailContent(email, otp);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text, true);

            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String generateOtpEmailContent(String email, String otp) {
        return "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<title>Password Change OTP</title>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; color: #333; background-color: #f4f7fc; margin: 0; padding: 0; }" +
                ".email-container { width: 100%; max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); }" +
                ".email-header { text-align: center; margin-bottom: 20px; }" +
                ".email-header h1 { color: #4A90E2; font-size: 24px; }" +
                ".email-body { font-size: 16px; line-height: 1.5; padding: 20px; text-align: center; }" +
                ".otp-button { display: inline-block; padding: 12px 25px; color: white; background-color: #4A90E2; text-decoration: none; border-radius: 5px; font-size: 18px; }" +
                ".otp-button:hover { background-color: #357ABD; }" +
                ".email-footer { text-align: center; font-size: 12px; color: #aaa; margin-top: 20px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='email-container'>" +
                "<div class='email-header'>" +
                "<h1>Password Reset OTP</h1>" +
                "</div>" +
                "<div class='email-body'>" +
                "<p>Hi,</p>" +
                "<p>Your OTP for password change is: <strong>" + otp + "</strong></p>" +
                "<p>Please use this OTP within the next 3 minutes to reset your password.</p>" +
                "<p>If you did not request a password reset, please ignore this email.</p>" +
                "</div>" +
                "<div class='email-footer'>" +
                "<p>&copy; 2024 Gyanpath. All rights reserved.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    @Override
    public boolean sendVerificationEmail(String toEmail, String token) {
        String subject = "Email Verification - Quiz Management System";
        String verificationUrl = frontendUrl + "/verify-email?token=" + token + "&email=" + toEmail;
        String htmlContent = generateVerificationEmailContent(toEmail, verificationUrl);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String generateVerificationEmailContent(String toEmail, String verificationUrl) {
        return "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<title>Email Verification</title>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; color: #333; background-color: #f4f7fc; margin: 0; padding: 0; }" +
                ".email-container { width: 100%; max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); }" +
                ".email-header { text-align: center; margin-bottom: 20px; }" +
                ".email-header h1 { color: #4A90E2; font-size: 24px; }" +
                ".email-body { font-size: 16px; line-height: 1.5; padding: 20px; text-align: center; }" +
                ".verification-button { display: inline-block; padding: 12px 25px; color: white; background-color: #4A90E2; text-decoration: none; border-radius: 5px; font-size: 18px; }" +
                ".verification-button:hover { background-color: #357ABD; }" +
                ".email-footer { text-align: center; font-size: 12px; color: #aaa; margin-top: 20px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='email-container'>" +
                "<div class='email-header'>" +
                "<h1>Welcome to Gyanpath</h1>" +
                "<p>We are excited to have you on board!</p>" +
                "</div>" +
                "<div class='email-body'>" +
                "<p>Hi,</p>" +
                "<p>Thank you for registering with us. Please verify your email by clicking the button below:</p>" +
                "<a href='" + verificationUrl + "' class='verification-button'>Verify Your Email</a>" +
                "<p>If you did not register for this account, please ignore this email.</p>" +
                "</div>" +
                "<div class='email-footer'>" +
                "<p>&copy; 2024 Gyanpath. All rights reserved.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}
