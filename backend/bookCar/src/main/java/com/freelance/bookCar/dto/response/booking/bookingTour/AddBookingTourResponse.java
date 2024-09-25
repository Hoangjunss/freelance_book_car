package com.freelance.bookCar.dto.response.booking.bookingTour;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddBookingTourResponse {
    private Integer idBooking;
    private Integer idTour;
    private Integer quantity;
    private double totalPrice;
    private Integer idUser;
}
