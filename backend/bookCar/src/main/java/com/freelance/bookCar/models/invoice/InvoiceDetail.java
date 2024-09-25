package com.freelance.bookCar.models.invoice;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InvoiceDetail {
    @Id
    private Integer id;
    private Integer idTour;
    private Integer idTourism;
    private Integer idHotel;
    private Integer idInvoice;
    private Integer quantity;
    private Double totalPrice;
}
