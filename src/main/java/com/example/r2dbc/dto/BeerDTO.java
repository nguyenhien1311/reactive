package com.example.r2dbc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerDTO {
    private Integer id;
    @NotBlank
    @Size(max = 255, min = 3)
    private String beerName;
    @Size(max = 255, min = 3)
    private String beerStyle;
    @Size(max = 255, min = 3)
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
}
