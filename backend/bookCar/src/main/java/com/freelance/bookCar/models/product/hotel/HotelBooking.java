package com.freelance.bookCar.models.product.hotel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HotelBooking {
    @Id
    private Integer id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double totalPrice;
    private Integer hotel;
    // Getters and Setters
}