package com.freelance.bookCar.models.product.voucher;

import jakarta.persistence.Access;
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
public class Voucher {
    @Id
    private Integer id;
    private LocalDateTime createDate;
    private boolean isUse;
    private Integer idPromotion;
}
