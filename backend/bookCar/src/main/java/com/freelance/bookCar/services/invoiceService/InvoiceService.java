package com.freelance.bookCar.services.invoiceService;

import com.freelance.bookCar.dto.request.invoice.CreateInvoiceRequest;
import com.freelance.bookCar.dto.request.invoice.UpdateInvoiceRequest;
import com.freelance.bookCar.dto.response.invoiceDTO.CreateInvoiceResponse;
import com.freelance.bookCar.dto.response.invoiceDTO.GetInvoiceResponse;
import com.freelance.bookCar.dto.response.invoiceDTO.UpdateInvoiceResponse;

public interface InvoiceService {
    CreateInvoiceResponse create(CreateInvoiceRequest createInvoiceRequest);
    UpdateInvoiceResponse update(UpdateInvoiceRequest updateInvoiceRequest);
    GetInvoiceResponse findById(Integer id);
}
