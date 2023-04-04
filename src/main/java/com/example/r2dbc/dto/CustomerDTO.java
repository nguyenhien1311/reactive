package com.example.r2dbc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomerDTO {
    private Integer id;
    @NotBlank
    @Size(min = 3, max = 255)
    private String fullName;
}
