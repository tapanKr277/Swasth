package com.org.swasth_id_backend.dto;

import java.util.List;
import java.util.UUID;

public class PatientDTO {
    private UUID id;
    private String fullName;
    private String email;
    private String phone;
    private String bloodGroup;
    private List<TreatmentDTO> treatments;
}
