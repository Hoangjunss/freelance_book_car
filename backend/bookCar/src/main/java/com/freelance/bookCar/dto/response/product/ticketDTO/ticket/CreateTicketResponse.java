package com.freelance.bookCar.dto.response.product.ticketDTO.ticket;

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

    public class CreateTicketResponse {
        private Integer id;
        private LocalDateTime startDate;
        private double tourPrice;

        // Getters and Setters
    }

