package com.freelance.bookCar.services.invoiceService;

import com.freelance.bookCar.dto.request.invoice.CreateInvoiceRequest;
import com.freelance.bookCar.dto.request.invoice.UpdateInvoiceRequest;
import com.freelance.bookCar.dto.response.invoiceDTO.CreateInvoiceResponse;
import com.freelance.bookCar.dto.response.invoiceDTO.GetInvoiceResponse;
import com.freelance.bookCar.dto.response.invoiceDTO.UpdateInvoiceResponse;
import com.freelance.bookCar.models.booking.Booking;
import com.freelance.bookCar.models.booking.BookingDetail;
import com.freelance.bookCar.models.invoice.Invoice;

import java.util.List;

public interface InvoiceService {
    CreateInvoiceResponse create(CreateInvoiceRequest createInvoiceRequest);
    UpdateInvoiceResponse update(UpdateInvoiceRequest updateInvoiceRequest);
    GetInvoiceResponse findById(Integer id);
    Invoice convertBookingToInvoice(Booking booking, List<BookingDetail> bookingDetails);
}
