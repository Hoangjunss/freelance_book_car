package com.freelance.bookCar.services.product.tourSchedule;

import com.freelance.bookCar.dto.request.product.tourSchedule.CreateTourScheduleRequest;
import com.freelance.bookCar.dto.request.product.tourSchedule.UpdateTourScheduleRequest;
import com.freelance.bookCar.dto.response.product.tourSchedule.CreateTourScheduleResponse;
import com.freelance.bookCar.dto.response.product.tourSchedule.GetTourScheduleResponse;
import com.freelance.bookCar.dto.response.product.tourSchedule.UpdateTourScheduleResponse;

public interface TourScheduleService {
    CreateTourScheduleResponse createTourSchedule(CreateTourScheduleRequest createTourScheduleRequest);
    UpdateTourScheduleResponse updateTourSchedule(UpdateTourScheduleRequest updateTourScheduleRequest);
    GetTourScheduleResponse findById(Integer id);
}
