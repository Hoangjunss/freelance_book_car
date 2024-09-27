package com.freelance.bookCar.models.booking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    private Integer id;
    private LocalDateTime dateBook;
    private double totalPrice;
    private Integer idUser;
    private Integer idTour;
    private Integer idPayment;
    private Integer idVoucher;
    private Integer idPromotion;
    @Enumerated(EnumType.STRING)
    private TypeBooking typeBooking;
    // Getters and Setters
}