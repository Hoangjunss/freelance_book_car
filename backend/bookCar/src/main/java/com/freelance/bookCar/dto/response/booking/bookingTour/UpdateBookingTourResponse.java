package com.freelance.bookCar.dto.response.booking.bookingTour;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingTourResponse {
    private Integer idBooking;
    private Integer idTour;
    private Integer quantity;
    private Double totalPrice;
}
