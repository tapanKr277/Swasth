package com.org.swasth_id_backend.controller;

import com.org.swasth_id_backend.dto.EmailDetails;
import com.org.swasth_id_backend.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/email")
@Tag(name = "Email controller")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendMail")
    public ResponseEntity<Map<String, String>> sendMail(@RequestBody EmailDetails details)
    {
        Map<String, String> response = new HashMap<>();
        emailService.sendSimpleMail(details);
        response.put("success", "Thank you for reaching out! Your message has been sent successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details)
    {
        String status = emailService.sendMailWithAttachment(details);
        return status;
    }


}
