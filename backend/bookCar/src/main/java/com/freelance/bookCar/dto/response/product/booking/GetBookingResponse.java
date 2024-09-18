package com.freelance.bookCar.dto.response.product.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetBookingResponse {
    private Integer id;
    private LocalDateTime dateBook;
    private double totalPrice;
    private Integer user;
    private Integer paymentMethod;
    private Integer invoice;
}
