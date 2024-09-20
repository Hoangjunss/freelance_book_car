package com.freelance.bookCar.dto.response.product.voucherDTO.promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePromotionResponse {
    private Integer id;
    private String name;
    private Float discountRate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
}
