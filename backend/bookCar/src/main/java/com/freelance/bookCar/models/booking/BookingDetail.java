package com.freelance.bookCar.models.booking;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookingDetail {
    @Id
    private Integer id;
    private Integer idTour;
    private Integer idTicket;
    private Integer idHotel;
    private Integer idVoucher;
    private Integer idBooking;
    private Integer quantity;
    private double totalPrice;
}
