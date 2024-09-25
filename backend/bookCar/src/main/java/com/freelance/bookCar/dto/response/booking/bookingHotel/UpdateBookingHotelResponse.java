package com.freelance.bookCar.dto.response.booking.bookingHotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingHotelResponse {
    private Integer idBooking;
    private Integer idHotel;
    private Integer quantity;
    private Double totalPrice;
}
