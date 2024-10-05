package com.freelance.bookCar.dto.request.booking.bookingHotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddBookingHotelRequest {
    private Integer idBooking;
    private Integer idHotel;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer quantity;
    private Double totalPrice;
    private Integer idUser;
}
