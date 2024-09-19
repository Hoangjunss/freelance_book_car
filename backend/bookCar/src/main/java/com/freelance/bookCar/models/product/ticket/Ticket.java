package com.freelance.bookCar.models.product.ticket;

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
public class Ticket {
    @Id
    private Integer id;
    private LocalDateTime startDate;
    private double tourPrice;
    private Integer idTourism;
}