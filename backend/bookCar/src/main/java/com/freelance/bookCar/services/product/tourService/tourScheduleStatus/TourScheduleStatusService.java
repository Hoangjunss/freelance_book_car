package com.freelance.bookCar.services.product.tourService.tourScheduleStatus;

import com.freelance.bookCar.dto.request.product.tourDTO.tourScheduleStatus.CreateTourScheduleStatusRequest;
import com.freelance.bookCar.dto.request.product.tourDTO.tourScheduleStatus.UpdateTourScheduleStatusRequest;
import com.freelance.bookCar.dto.response.product.tourDTO.tourScheduleStatus.CreateTourScheduleStatusResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourScheduleStatus.GetTourScheduleStatusResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourScheduleStatus.UpdateTourScheduleStatusResponse;
import org.springframework.stereotype.Service;

@Service
public interface TourScheduleStatusService {
    CreateTourScheduleStatusResponse create(CreateTourScheduleStatusRequest createTourScheduleStatusRequest);
    UpdateTourScheduleStatusResponse update(UpdateTourScheduleStatusRequest updateTourScheduleStatusRequest);
    GetTourScheduleStatusResponse findById(Integer id);
}
