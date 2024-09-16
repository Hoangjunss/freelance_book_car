package com.freelance.bookCar.models.booking;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    private Integer user;
    private Integer paymentMethod;


    private Integer invoice;

    // Getters and Setters
}