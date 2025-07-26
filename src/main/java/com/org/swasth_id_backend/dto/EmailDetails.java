package com.org.swasth_id_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {

    private String senderName;
    private String message;
    private String senderEmail;
    private String attachment;

}
