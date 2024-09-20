package com.freelance.bookCar.controller.product.voucher;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.product.voucherDTO.voucher.CreateVoucherRequest;
import com.freelance.bookCar.dto.request.product.voucherDTO.voucher.UpdateVoucherRequest;
import com.freelance.bookCar.dto.response.product.voucherDTO.voucher.CreateVoucherResponse;
import com.freelance.bookCar.dto.response.product.voucherDTO.voucher.GetVoucherResponse;
import com.freelance.bookCar.dto.response.product.voucherDTO.voucher.UpdateVoucherResponse;
import com.freelance.bookCar.services.product.voucherService.voucherService.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @PostMapping()
    public ResponseEntity<ApiResponse<CreateVoucherResponse>> create(@RequestBody CreateVoucherRequest createVoucherRequest) {
        CreateVoucherResponse createVoucherResponse = voucherService.create(createVoucherRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Voucher created successfully", createVoucherResponse));
    }

    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateVoucherResponse>> update(@RequestBody UpdateVoucherRequest updateVoucherRequest) {
        UpdateVoucherResponse updateVoucherResponse = voucherService.update(updateVoucherRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Voucher updated successfully", updateVoucherResponse));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<GetVoucherResponse>> getVoucher(@RequestParam Integer id) {
        GetVoucherResponse getVoucherResponse = voucherService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", getVoucherResponse));
    }
}