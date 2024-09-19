package com.freelance.bookCar.models.invoice;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Invoice {
    @Id
    private Integer id;
    private LocalDateTime invoiceDate;
    private double totalAmount;
    private String paymentStatus;
    private Integer idBooking;
}