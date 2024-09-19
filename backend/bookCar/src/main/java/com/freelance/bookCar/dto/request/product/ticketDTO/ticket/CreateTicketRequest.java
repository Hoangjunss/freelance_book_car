package com.freelance.bookCar.dto.request.product.ticketDTO.ticket;

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

    public class CreateTicketRequest {
        private LocalDateTime startDate;
        private double tourPrice;
        private Integer idTourism;
        // Getters and Setters
    }

