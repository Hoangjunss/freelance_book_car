package com.freelance.bookCar.services.product.voucherService.voucherService;

import com.freelance.bookCar.dto.request.product.voucherDTO.voucher.CreateVoucherRequest;
import com.freelance.bookCar.dto.request.product.voucherDTO.voucher.UpdateVoucherRequest;
import com.freelance.bookCar.dto.response.product.voucherDTO.voucher.CreateVoucherResponse;
import com.freelance.bookCar.dto.response.product.voucherDTO.voucher.GetVoucherResponse;
import com.freelance.bookCar.dto.response.product.voucherDTO.voucher.UpdateVoucherResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VoucherService {
    CreateVoucherResponse create(CreateVoucherRequest createVoucherRequest);
    UpdateVoucherResponse update(UpdateVoucherRequest updateVoucherRequest);
    GetVoucherResponse findById(Integer id);
    List<GetVoucherResponse> getAll();
    GetVoucherResponse findByName(String name);
}
