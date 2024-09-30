package com.freelance.bookCar.controller.invoice;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.invoice.CreateInvoiceRequest;
import com.freelance.bookCar.dto.request.invoice.UpdateInvoiceRequest;
import com.freelance.bookCar.dto.response.invoiceDTO.CreateInvoiceResponse;
import com.freelance.bookCar.dto.response.invoiceDTO.GetInvoiceResponse;
import com.freelance.bookCar.dto.response.invoiceDTO.UpdateInvoiceResponse;
import com.freelance.bookCar.services.invoiceService.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
@CrossOrigin(origins = "*")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping()
    public ResponseEntity<ApiResponse<CreateInvoiceResponse>> create(@ModelAttribute @Valid CreateInvoiceRequest createInvoiceRequest){
        CreateInvoiceResponse createInvoiceResponse=invoiceService.create(createInvoiceRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Invoice created successfully", createInvoiceResponse));
    }

    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateInvoiceResponse>> update(@ModelAttribute @Valid UpdateInvoiceRequest updateInvoiceRequest){
        UpdateInvoiceResponse updateInvoiceResponse=invoiceService.update(updateInvoiceRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Invoice updated successfully", updateInvoiceResponse));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<GetInvoiceResponse>> getInvoiceById(@RequestParam Integer id){
        GetInvoiceResponse getInvoiceResponse=invoiceService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Id Invoice successfully", getInvoiceResponse));
    }
}
