package com.freelance.bookCar.dto.response.product.ticketDTO.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UpdateTicketResponse {
    private Integer id;
    private LocalDateTime startDate;
    private double tourPrice;

    // Getters and Setters
}

