package com.freelance.bookCar.controller.product.voucher;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.product.voucherDTO.promotion.CreatePromotionRequest;
import com.freelance.bookCar.dto.request.product.voucherDTO.promotion.UpdatePromotionRequest;
import com.freelance.bookCar.dto.response.product.voucherDTO.promotion.CreatePromotionResponse;
import com.freelance.bookCar.dto.response.product.voucherDTO.promotion.GetPromotionResponse;
import com.freelance.bookCar.dto.response.product.voucherDTO.promotion.UpdatePromotionResponse;
import com.freelance.bookCar.services.product.voucherService.promotionService.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping()
    public ResponseEntity<ApiResponse<CreatePromotionResponse>> create(@RequestBody CreatePromotionRequest createPromotionRequest) {
        CreatePromotionResponse createPromotionResponse = promotionService.create(createPromotionRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Promotion created successfully", createPromotionResponse));
    }

    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdatePromotionResponse>> update(@RequestBody UpdatePromotionRequest updatePromotionRequest) {
        UpdatePromotionResponse updatePromotionResponse = promotionService.update(updatePromotionRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Promotion updated successfully", updatePromotionResponse));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<GetPromotionResponse>> getPromotion(@RequestParam Integer id) {
        GetPromotionResponse getPromotionResponse = promotionService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Promotion successfully", getPromotionResponse));
    }
}