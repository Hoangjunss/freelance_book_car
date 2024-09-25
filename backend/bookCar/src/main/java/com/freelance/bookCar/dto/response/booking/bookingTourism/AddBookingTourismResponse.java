package com.freelance.bookCar.dto.response.booking.bookingTourism;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddBookingTourismResponse {
    private Integer idBooking;
    private Integer idTourism;
    private Integer quantity;
    private Double totalPrice;
    private Integer idUser;
}
