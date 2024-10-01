package com.freelance.bookCar.controller.product.voucher;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.product.voucherDTO.voucher.CreateVoucherRequest;
import com.freelance.bookCar.dto.request.product.voucherDTO.voucher.UpdateVoucherRequest;
import com.freelance.bookCar.dto.response.product.voucherDTO.voucher.CreateVoucherResponse;
import com.freelance.bookCar.dto.response.product.voucherDTO.voucher.GetVoucherResponse;
import com.freelance.bookCar.dto.response.product.voucherDTO.voucher.UpdateVoucherResponse;
import com.freelance.bookCar.services.product.voucherService.voucherService.VoucherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/voucher")
@CrossOrigin(origins = "*")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping()
    public ResponseEntity<ApiResponse<CreateVoucherResponse>> create(@ModelAttribute @Valid CreateVoucherRequest createVoucherRequest) {
        CreateVoucherResponse createVoucherResponse = voucherService.create(createVoucherRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Voucher created successfully", createVoucherResponse));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateVoucherResponse>> update(@ModelAttribute @Valid UpdateVoucherRequest updateVoucherRequest) {
        UpdateVoucherResponse updateVoucherResponse = voucherService.update(updateVoucherRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Voucher updated successfully", updateVoucherResponse));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping()
    public ResponseEntity<ApiResponse<GetVoucherResponse>> getVoucher(@RequestParam Integer id) {
        GetVoucherResponse getVoucherResponse = voucherService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", getVoucherResponse));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<GetVoucherResponse>>> getAllVouchers() {
        List<GetVoucherResponse> getVoucherResponse = voucherService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Get all vouchers successfully", getVoucherResponse));
    }
}