package com.org.swasth_id_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "consultations")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "visit_reason", length = 200)
    private String visitReason;

    @Column(name = "diagnosis", length = 200)
    private String diagnosis;

    @Column(name = "notes", length = 255, nullable = true)
    private String notes;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "visit_date", nullable = false)
    private Date visitDate;

    @Column(name = "is_follow_up_required", nullable = false)
    private Boolean isFollowUpRequired = false;

    @Temporal(TemporalType.DATE)
    private Date followUpDate;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TestReport> testReports;

    @OneToOne(mappedBy = "consultation", cascade = CascadeType.ALL)
    @JsonManagedReference
    private FollowUp followUp;
}