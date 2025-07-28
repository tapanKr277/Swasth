package com.org.swasth_id_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.org.swasth_id_backend.utils.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "follow_ups")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowUp extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    @Temporal(TemporalType.DATE)
    @Column(name = "scheduled_date", nullable = false)
    private Date scheduledDate;

    @JsonFormat(pattern = "HH:mm")
    @Column(name = "scheduled_time", nullable = false)
    private LocalTime scheduledTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.UPCOMING;

    @Column(name = "notes", nullable = true)
    private String notes;


}