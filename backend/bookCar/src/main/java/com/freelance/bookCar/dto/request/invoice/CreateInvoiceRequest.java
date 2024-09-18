package com.freelance.bookCar.dto.request.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
    private double totalAmount;
    private String paymentStatus;
    private Integer idBooking;
}
