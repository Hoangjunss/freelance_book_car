package com.freelance.bookCar.dto.request.product.voucherDTO.promotion;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePromotionRequest {
    private Integer id;
    private String name;
    private Float discountRate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
}
