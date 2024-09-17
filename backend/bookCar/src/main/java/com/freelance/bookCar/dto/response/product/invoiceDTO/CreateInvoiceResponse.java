package com.freelance.bookCar.dto.response.product.invoiceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceResponse {
        private Integer id;
        private LocalDateTime invoiceDate;
        private double totalAmount;
        private String paymentStatus;
        private Integer booking;
}
