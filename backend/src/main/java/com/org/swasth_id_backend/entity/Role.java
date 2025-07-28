package com.org.swasth_id_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
@Builder
public class Role extends BaseEntity{

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "description")
    private String description;
    
}