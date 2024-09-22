package com.freelance.bookCar.dto.request.product.hotelDTO.hotelBooking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHotelBookingRequest {
    private Integer id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double totalPrice;
    private Integer hotel;
}