package com.freelance.bookCar.models.product.voucher;

import com.freelance.bookCar.models.booking.Booking;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Voucher {
    @Id
    private Integer id;
    private LocalDateTime endDate;
    private boolean isUse;
    private String name;
    private Float discountRate;
}
