package com.org.swasth_id_backend.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TreatmentDTO {
    private UUID id;
    private String doctorName;
    private String diagnosis;
    private List<PrescriptionDTO> prescriptions;
    private LocalDate followUpDate;
    private boolean isFollowUpRequired;
}
