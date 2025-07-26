package com.org.swasth_id_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Doctor extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, length = 100)
    private String specialization;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "license_number", unique = true, length = 50)
    private String licenseNumber;

    @Column(name = "year_of_experience")
    private Integer yearsOfExperience = 0;

    @Column(name = "hospitalName")
    private String hospitalName;

    @Column(name = "available_timings")
    private String availableTimings;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DoctorPatient> doctorPatients = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Consultation> consultations = new ArrayList<>();

}
