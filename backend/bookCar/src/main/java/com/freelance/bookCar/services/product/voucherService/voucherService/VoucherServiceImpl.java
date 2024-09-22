package com.freelance.bookCar.services.product.voucherService.voucherService;

import com.freelance.bookCar.dto.request.product.voucherDTO.voucher.CreateVoucherRequest;
import com.freelance.bookCar.dto.request.product.voucherDTO.voucher.UpdateVoucherRequest;
import com.freelance.bookCar.dto.response.product.voucherDTO.voucher.CreateVoucherResponse;
import com.freelance.bookCar.dto.response.product.voucherDTO.voucher.GetVoucherResponse;
import com.freelance.bookCar.dto.response.product.voucherDTO.voucher.UpdateVoucherResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.product.voucher.Voucher;
import com.freelance.bookCar.respository.product.voucher.VoucherRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class VoucherServiceImpl implements VoucherService{
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CreateVoucherResponse create(CreateVoucherRequest createVoucherRequest) {
        log.info("Create voucher");

        // Kiểm tra tính hợp lệ của yêu cầu
        if (createVoucherRequest.getIdPromotion() == null) {
            throw new CustomException(Error.VOUCHER_INVALID_PROMOTION_ID);
        }
        if (createVoucherRequest.getCreateDate() == null) {
            throw new CustomException(Error.VOUCHER_INVALID_CREATE_DATE);
        }

        // Tạo đối tượng Voucher
        Voucher voucher = Voucher.builder()
                .id(generateId())
                .createDate(createVoucherRequest.getCreateDate())
                .isUse(createVoucherRequest.isUse())
                .idPromotion(createVoucherRequest.getIdPromotion())
                .build();

        try {
            // Lưu Voucher vào cơ sở dữ liệu và trả về response
            return modelMapper.map(voucherRepository.save(voucher), CreateVoucherResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while saving Voucher: {}", e.getMessage(), e);
            throw new CustomException(Error.VOUCHER_UNABLE_TO_SAVE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public UpdateVoucherResponse update(UpdateVoucherRequest updateVoucherRequest) {
        log.info("Update voucher with id: {}", updateVoucherRequest.getId());

        if (updateVoucherRequest.getId() == null) {
            throw new CustomException(Error.VOUCHER_NOT_FOUND);
        }

        // Tìm Voucher hiện tại theo ID
        Voucher voucher = modelMapper.map(findById(updateVoucherRequest.getId()), Voucher.class);

        // Cập nhật các trường nếu không null
        if (updateVoucherRequest.getCreateDate() != null) {
            voucher.setCreateDate(updateVoucherRequest.getCreateDate());
        }
        voucher.setUse(updateVoucherRequest.isUse());

        if (updateVoucherRequest.getIdPromotion() != null) {
            voucher.setIdPromotion(updateVoucherRequest.getIdPromotion());
        }

        try {
            // Lưu Voucher cập nhật vào cơ sở dữ liệu và trả về response
            return modelMapper.map(voucherRepository.save(voucher), UpdateVoucherResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while updating Voucher: {}", e.getMessage(), e);
            throw new CustomException(Error.VOUCHER_UNABLE_TO_UPDATE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public GetVoucherResponse findById(Integer id) {
        // Tìm Voucher theo ID, nếu không tìm thấy thì ném ngoại lệ
        return modelMapper.map(voucherRepository.findById(id).orElseThrow(() ->
                new CustomException(Error.VOUCHER_NOT_FOUND)), GetVoucherResponse.class);
    }

    private Integer generateId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
