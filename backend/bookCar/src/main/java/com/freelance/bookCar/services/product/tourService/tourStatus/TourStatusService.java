package com.freelance.bookCar.services.product.tourService.tourStatus;

import com.freelance.bookCar.dto.request.product.tourDTO.tourStatus.CreateTourStatusRequest;
import com.freelance.bookCar.dto.request.product.tourDTO.tourStatus.UpdateTourStatusRequest;
import com.freelance.bookCar.dto.response.product.tourDTO.tourStatus.CreateTourStatusResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourStatus.GetTourStatusResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourStatus.UpdateTourStatusResponse;
import org.springframework.stereotype.Service;

@Service
public interface TourStatusService {
    CreateTourStatusResponse create(CreateTourStatusRequest createTourStatusRequest);
    UpdateTourStatusResponse update(UpdateTourStatusRequest updateTourStatusRequest);
    GetTourStatusResponse findById(Integer id);
}
