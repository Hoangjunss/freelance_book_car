package com.freelance.bookCar.models.invoice;

import jakarta.persistence.Id;

public class InvoiceDetail {
    @Id
    private Integer id;
    private Integer idTour;
    private Integer idTourism;
    private Integer idHotel;
    private Integer idInvoice;
    private Integer quantity;
    private Integer totalPrice;
}
