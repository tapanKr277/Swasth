package com.org.swasth_id_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private UUID roleId;
    private String role;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;
}
