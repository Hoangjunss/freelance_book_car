package com.freelance.bookCar.models.booking;

import jakarta.persistence.Id;

public class BookingDetail {
    @Id
    private Integer id;
    private Integer idTour;
    private Integer idTourism;
    private Integer idHotel;
    private Integer idBooking;
    private Integer quantity;
    private Integer totalPrice;

}
