package com.org.swasth_id_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "test_reports")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestReport extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id", referencedColumnName = "id")
    private Consultation consultation;

    @Column(name = "test_type", nullable = false, length = 255)
    private String testType;

    @Column(name = "result_summary", length = 255)
    private String resultSummary;

    @Column(name = "report_file_url", length = 255)
    private String reportFileUrl;

    @Temporal(TemporalType.DATE)
    @Column(name = "test_date")
    private Date testDate;

}