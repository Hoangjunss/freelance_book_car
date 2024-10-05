package com.freelance.bookCar.dto.request.booking.bookingTour;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddBookingTourRequest {
    private Integer idBooking;
    private Integer idTour;
    private Integer quantity;
    private String totalPrice;
    private Integer idUser;
}
