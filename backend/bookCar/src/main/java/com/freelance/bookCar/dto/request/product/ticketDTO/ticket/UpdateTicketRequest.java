package com.freelance.bookCar.dto.request.product.ticketDTO.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UpdateTicketRequest {
    private Integer id;
    private LocalDateTime startDate;
    private double tourPrice;
    private Integer idTourism;
    // Getters and Setters
}

