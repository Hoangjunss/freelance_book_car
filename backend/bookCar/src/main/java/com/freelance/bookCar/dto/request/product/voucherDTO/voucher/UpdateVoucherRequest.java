package com.freelance.bookCar.dto.request.product.voucherDTO.voucher;

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
public class UpdateVoucherRequest {
    private Integer id;
    private LocalDateTime endDate;
    private boolean isUse;
    private String name;
    private Float discountRate;
}
