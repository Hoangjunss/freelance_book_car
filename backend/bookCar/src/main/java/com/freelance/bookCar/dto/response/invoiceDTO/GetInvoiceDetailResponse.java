package com.freelance.bookCar.dto.response.invoiceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetInvoiceDetailResponse {
    private Integer id;
    private Integer idTour;
    private Integer idTourism;
    private Integer idHotel;
    private Integer idInvoice;
    private Integer quantity;
    private Double totalPrice;
}
