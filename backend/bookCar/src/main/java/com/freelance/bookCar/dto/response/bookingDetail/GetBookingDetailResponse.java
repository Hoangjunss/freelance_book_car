package com.freelance.bookCar.dto.response.bookingDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetBookingDetailResponse {
    private Integer id;
    private Integer idTour;
    private Integer idTicket;
    private Integer idHotel;
    private Integer idBooking;
    private Integer quantity;
    private double totalPrice;
}
