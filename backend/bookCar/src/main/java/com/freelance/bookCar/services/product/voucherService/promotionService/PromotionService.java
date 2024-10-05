package com.freelance.bookCar.services.product.voucherService.promotionService;

import com.freelance.bookCar.dto.request.product.voucherDTO.promotion.CreatePromotionRequest;
import com.freelance.bookCar.dto.request.product.voucherDTO.promotion.UpdatePromotionRequest;
import com.freelance.bookCar.dto.response.product.voucherDTO.promotion.CreatePromotionResponse;
import com.freelance.bookCar.dto.response.product.voucherDTO.promotion.GetPromotionResponse;
import com.freelance.bookCar.dto.response.product.voucherDTO.promotion.UpdatePromotionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PromotionService {
    CreatePromotionResponse create(CreatePromotionRequest createPromotionRequest);
    UpdatePromotionResponse update(UpdatePromotionRequest updatePromotionRequest);
    GetPromotionResponse findById(Integer id);
    List<GetPromotionResponse> getAll();
}
