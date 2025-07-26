package com.org.swasth_id_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO for handling user profile updates.
 * Contains only the fields that are safe for a user to modify.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Min(value = 0, message = "Age cannot be negative")
    private Integer age;

    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood group format. Use format like 'A+', 'O-', etc.")
    private String bloodGroup;

}