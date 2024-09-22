package com.freelance.bookCar.dto.response.invoiceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetInvoiceResponse {
    private Integer id;
    private LocalDateTime invoiceDate;
    private double totalAmount;
    private boolean isPaymentStatus;
    private Integer idBooking;
}
