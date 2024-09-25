package com.freelance.bookCar.services.invoiceService;

import com.freelance.bookCar.dto.request.invoice.CreateInvoiceRequest;
import com.freelance.bookCar.dto.request.invoice.UpdateInvoiceRequest;
import com.freelance.bookCar.dto.response.invoiceDTO.CreateInvoiceResponse;
import com.freelance.bookCar.dto.response.invoiceDTO.GetInvoiceResponse;
import com.freelance.bookCar.dto.response.invoiceDTO.UpdateInvoiceResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.invoice.Invoice;
import com.freelance.bookCar.respository.invoice.InvoiceResponsitory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        if (createInvoiceRequest.getTotalAmount() < 0) {
            throw new CustomException(Error.INVOICE_INVALID_TOTAL_AMOUNT);
        }
        if (createInvoiceRequest.getIdBooking() == null){
            throw new CustomException(Error.INVOICE_INVALID_ID_BOOKING);
        }

        Invoice invoice = Invoice.builder()
                .id(getGenerationId())
                .invoiceDate(LocalDateTime.now())
                .totalAmount(createInvoiceRequest.getTotalAmount())
                .isPaymentStatus(createInvoiceRequest.isPaymentStatus())
                .idBooking(createInvoiceRequest.getIdBooking())
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

        if (updateInvoiceRequest.getInvoiceDate() != null){
            existingInvoice.setInvoiceDate(updateInvoiceRequest.getInvoiceDate());
        }
        if (updateInvoiceRequest.getIdBooking() != null){
            existingInvoice.setIdBooking(updateInvoiceRequest.getIdBooking());
        }
        if (updateInvoiceRequest.getTotalAmount() > 0D){
            existingInvoice.setTotalAmount(updateInvoiceRequest.getTotalAmount());
        }
            existingInvoice.setPaymentStatus(updateInvoiceRequest.isPaymentStatus());

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
