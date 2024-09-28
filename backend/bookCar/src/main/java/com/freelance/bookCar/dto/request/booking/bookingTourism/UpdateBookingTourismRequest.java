package com.freelance.bookCar.dto.request.booking.bookingTourism;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingTourismRequest {
    private Integer idBooking;
    private Integer idTicket;
    private Integer quantity;
}
