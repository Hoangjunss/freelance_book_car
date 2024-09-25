package com.freelance.bookCar.dto.request.booking.bookingHotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingHotelRequest {
    private Integer idBooking;
    private Integer idHotel;
    private Integer quantity;
}
