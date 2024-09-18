package com.freelance.bookCar.services.product.invoiceService;

import com.freelance.bookCar.dto.request.invoice.CreateInvoiceRequest;
import com.freelance.bookCar.dto.request.invoice.UpdateInvoiceRequest;
import com.freelance.bookCar.dto.response.product.invoiceDTO.CreateInvoiceResponse;
import com.freelance.bookCar.dto.response.product.invoiceDTO.GetInvoiceResponse;
import com.freelance.bookCar.dto.response.product.invoiceDTO.UpdateInvoiceResponse;

public interface InvoiceService {
    CreateInvoiceResponse create(CreateInvoiceRequest createInvoiceRequest);
    UpdateInvoiceResponse update(UpdateInvoiceRequest updateInvoiceRequest);
    GetInvoiceResponse findById(Integer id);
}
