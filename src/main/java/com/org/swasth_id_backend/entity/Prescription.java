package com.org.swasth_id_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescriptions")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "consultation_id", referencedColumnName = "id")
    private Consultation consultation;

    @Column(name = "medicine_name", nullable = false, length = 255)
    private String medicineName;

    @Column(name = "dosage", nullable = false, length = 255)
    private String dosage;

    @Column(name = "duration", nullable = false, length = 255)
    private String duration;

    @Column(name = "instructions", nullable = false, length = 255)
    private String instructions;

}
