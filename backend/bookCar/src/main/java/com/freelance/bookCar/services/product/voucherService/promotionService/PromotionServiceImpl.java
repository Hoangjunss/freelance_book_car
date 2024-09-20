package com.freelance.bookCar.services.product.voucherService.promotionService;

import com.freelance.bookCar.dto.request.product.voucherDTO.promotion.CreatePromotionRequest;
import com.freelance.bookCar.dto.request.product.voucherDTO.promotion.UpdatePromotionRequest;
import com.freelance.bookCar.dto.response.product.voucherDTO.promotion.CreatePromotionResponse;
import com.freelance.bookCar.dto.response.product.voucherDTO.promotion.GetPromotionResponse;
import com.freelance.bookCar.dto.response.product.voucherDTO.promotion.UpdatePromotionResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.product.voucher.Promotion;
import com.freelance.bookCar.respository.product.voucher.PromotionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CreatePromotionResponse create(CreatePromotionRequest createPromotionRequest) {
        log.info("Create promotion");

        // Kiểm tra tính hợp lệ của yêu cầu
        if (createPromotionRequest.getName() == null || createPromotionRequest.getName().isEmpty()) {
            throw new CustomException(Error.PROMOTION_INVALID_NAME);
        }
        if (createPromotionRequest.getDiscountRate() == null || createPromotionRequest.getDiscountRate() < 0) {
            throw new CustomException(Error.PROMOTION_INVALID_DISCOUNT_RATE);
        }
        if (createPromotionRequest.getStartDate() == null || createPromotionRequest.getEndDate() == null ||
                createPromotionRequest.getEndDate().isBefore(createPromotionRequest.getStartDate())) {
            throw new CustomException(Error.PROMOTION_INVALID_DATE_RANGE);
        }

        // Tạo đối tượng Promotion
        Promotion promotion = Promotion.builder()
                .id(generateId())
                .name(createPromotionRequest.getName())
                .discountRate(createPromotionRequest.getDiscountRate())
                .startDate(createPromotionRequest.getStartDate())
                .endDate(createPromotionRequest.getEndDate())
                .description(createPromotionRequest.getDescription())
                .build();

        try {
            // Lưu Promotion vào cơ sở dữ liệu và trả về response
            return modelMapper.map(promotionRepository.save(promotion), CreatePromotionResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while saving Promotion: {}", e.getMessage(), e);
            throw new CustomException(Error.PROMOTION_UNABLE_TO_SAVE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public UpdatePromotionResponse update(UpdatePromotionRequest updatePromotionRequest) {
        log.info("Update promotion with id: {}", updatePromotionRequest.getId());

        if (updatePromotionRequest.getId() == null) {
            throw new CustomException(Error.PROMOTION_NOT_FOUND);
        }

        // Tìm Promotion hiện tại theo ID
        Promotion promotion = modelMapper.map(findById(updatePromotionRequest.getId()), Promotion.class);

        // Cập nhật các trường nếu không null
        if (updatePromotionRequest.getName() != null) {
            promotion.setName(updatePromotionRequest.getName());
        }
        if (updatePromotionRequest.getDiscountRate() != null && updatePromotionRequest.getDiscountRate() > 0) {
            promotion.setDiscountRate(updatePromotionRequest.getDiscountRate());
        }
        if (updatePromotionRequest.getStartDate() != null && updatePromotionRequest.getEndDate() != null &&
                updatePromotionRequest.getEndDate().isAfter(updatePromotionRequest.getStartDate())) {
            promotion.setStartDate(updatePromotionRequest.getStartDate());
            promotion.setEndDate(updatePromotionRequest.getEndDate());
        }
        if (updatePromotionRequest.getDescription() != null) {
            promotion.setDescription(updatePromotionRequest.getDescription());
        }

        try {
            // Lưu Promotion cập nhật vào cơ sở dữ liệu và trả về response
            return modelMapper.map(promotionRepository.save(promotion), UpdatePromotionResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while updating Promotion: {}", e.getMessage(), e);
            throw new CustomException(Error.PROMOTION_UNABLE_TO_UPDATE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public GetPromotionResponse findById(Integer id) {
        // Tìm Promotion theo ID, nếu không tìm thấy thì ném ngoại lệ
        return modelMapper.map(promotionRepository.findById(id).orElseThrow(() ->
                new CustomException(Error.PROMOTION_NOT_FOUND)), GetPromotionResponse.class);
    }

    private Integer generateId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
