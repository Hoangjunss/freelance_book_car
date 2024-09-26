package com.freelance.bookCar.services.product.tourService.tourSchedule;

import com.freelance.bookCar.dto.request.product.tourDTO.tourSchedule.CreateTourScheduleRequest;
import com.freelance.bookCar.dto.request.product.tourDTO.tourSchedule.UpdateTourScheduleRequest;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.CreateTourScheduleResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.GetTourScheduleResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.UpdateTourScheduleResponse;
import com.freelance.bookCar.models.product.tour.TourSchedule;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface TourScheduleService {
    CreateTourScheduleResponse createTourSchedule(CreateTourScheduleRequest createTourScheduleRequest);
    UpdateTourScheduleResponse updateTourSchedule(UpdateTourScheduleRequest updateTourScheduleRequest);
    GetTourScheduleResponse findById(Integer id);
    List<GetTourScheduleResponse> getAll();
    TourSchedule findByIdAndByStartDate(Integer id, LocalDateTime date);
}
