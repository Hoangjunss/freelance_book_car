package com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking;

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

public class CreateHotelBookingResponse {
    private Integer id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double totalPrice;
    private Integer hotel;
    // Getters and Setters
}