package com.freelance.bookCar.models.product.voucher;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Promotion {
    @Id
    private Integer id;
    private String name;
    private Float discountRate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
}
