package com.freelance.bookCar.controller.product.tour;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.product.tourDTO.tourSchedule.CreateTourScheduleRequest;
import com.freelance.bookCar.dto.request.product.tourDTO.tourSchedule.UpdateTourScheduleRequest;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.CreateTourScheduleResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.UpdateTourScheduleResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.GetTourScheduleResponse;
import com.freelance.bookCar.services.product.tourService.tourSchedule.TourScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tour-schedule")
@Slf4j
public class TourScheduleController {

    @Autowired
    private TourScheduleService tourScheduleService;

    @PostMapping()
    public ResponseEntity<ApiResponse<CreateTourScheduleResponse>> create(@RequestBody CreateTourScheduleRequest createTourScheduleRequest) {
            CreateTourScheduleResponse response = tourScheduleService.createTourSchedule(createTourScheduleRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tour schedule created successfully", response));
    }

    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateTourScheduleResponse>> update(@RequestBody UpdateTourScheduleRequest updateTourScheduleRequest) {
            UpdateTourScheduleResponse response = tourScheduleService.updateTourSchedule(updateTourScheduleRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tour schedule updated successfully", response));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<GetTourScheduleResponse>> getById(@RequestParam Integer id) {
            GetTourScheduleResponse response = tourScheduleService.findById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tour schedule retrieved successfully", response));
    }
}