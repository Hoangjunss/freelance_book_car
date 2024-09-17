package com.freelance.bookCar.services.product.invoiceService;

import com.freelance.bookCar.dto.request.product.invoiceDTO.CreateInvoiceRequest;
import com.freelance.bookCar.dto.request.product.invoiceDTO.UpdateInvoiceRequest;
import com.freelance.bookCar.dto.response.product.invoiceDTO.CreateInvoiceResponse;
import com.freelance.bookCar.dto.response.product.invoiceDTO.GetInvoiceResponse;
import com.freelance.bookCar.dto.response.product.invoiceDTO.UpdateInvoiceResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.invoice.Invoice;
import com.freelance.bookCar.respository.product.invoice.InvoiceResponsitory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService{
    @Autowired
    private InvoiceResponsitory invoiceRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CreateInvoiceResponse create(CreateInvoiceRequest createInvoiceRequest) {
        log.info("Creating invoice");

        // Validate input data
        if (createInvoiceRequest.getTotalAmount() <= 0) {
            throw new CustomException(Error.INVOICE_INVALID_TOTAL_AMOUNT);
        }

        if (createInvoiceRequest.getPaymentStatus() == null || createInvoiceRequest.getPaymentStatus().isEmpty()) {
            throw new CustomException(Error.INVOICE_INVALID_PAYMENT_STATUS);
        }
        if (createInvoiceRequest.getIdBooking() == null){
            throw new CustomException(Error.INVOICE_INVALID_ID_BOOKING);
        }

        // Create new Invoice object
        Invoice invoice = Invoice.builder()
                .id(getGenerationId())
                .totalAmount(createInvoiceRequest.getTotalAmount())
                .paymentStatus(createInvoiceRequest.getPaymentStatus())
                .booking(createInvoiceRequest.getIdBooking())
                .build();

        try {
            return modelMapper.map(invoiceRepository.save(invoice), CreateInvoiceResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while saving Invoice: {}", e.getMessage(), e);
            throw new CustomException(Error.INVOICE_UNABLE_TO_SAVE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public UpdateInvoiceResponse update(UpdateInvoiceRequest updateInvoiceRequest) {
        log.info("Updating invoice with ID: {}", updateInvoiceRequest.getId());
        if (updateInvoiceRequest.getId() == null) {
            throw new CustomException(Error.INVOICE_NOT_FOUND);
        }

        Invoice existingInvoice = invoiceRepository.findById(updateInvoiceRequest.getId())
                .orElseThrow(() -> new CustomException(Error.INVOICE_NOT_FOUND));

        // Update fieldsi
        if (updateInvoiceRequest.getInvoiceDate() != null){
            existingInvoice.setInvoiceDate(updateInvoiceRequest.getInvoiceDate());
        }
        if (updateInvoiceRequest.getBooking() != null){
            existingInvoice.setBooking(updateInvoiceRequest.getBooking());
        }
        if (updateInvoiceRequest.getTotalAmount() != existingInvoice.getTotalAmount()){
            existingInvoice.setTotalAmount(updateInvoiceRequest.getTotalAmount());
        }
        if (updateInvoiceRequest.getPaymentStatus() != existingInvoice.getPaymentStatus()){
            existingInvoice.setPaymentStatus(updateInvoiceRequest.getPaymentStatus());
        }

        try {
            return modelMapper.map(invoiceRepository.save(existingInvoice), UpdateInvoiceResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while updating Invoice: {}", e.getMessage(), e);
            throw new CustomException(Error.INVOICE_UNABLE_TO_UPDATE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public GetInvoiceResponse findById(Integer id) {
        log.info("Finding invoice with ID: {}", id);
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.INVOICE_NOT_FOUND));
        return modelMapper.map(invoice, GetInvoiceResponse.class);
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
