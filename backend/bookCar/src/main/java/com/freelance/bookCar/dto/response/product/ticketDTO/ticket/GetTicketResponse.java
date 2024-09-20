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

public class GetTicketResponse {
    private Integer id;
    private LocalDateTime startDate;
    private double tourPrice;
    private Integer idTourism;
    // Getters and Setters
}

