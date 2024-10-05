package com.freelance.bookCar.dto.response.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingResponse {
        private Integer id;
        private LocalDateTime dateBook;
        private double totalPrice;
        private Integer idUser;
        private Integer paymentMethod;
        private Integer invoice;
        private String type;
        // Getters and Setters
}
