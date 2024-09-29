package com.freelance.bookCar.controller.product.tour;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.product.tourDTO.tourSchedule.CreateTourScheduleRequest;
import com.freelance.bookCar.dto.request.product.tourDTO.tourSchedule.UpdateTourScheduleRequest;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.CreateTourScheduleResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.UpdateTourScheduleResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.GetTourScheduleResponse;
import com.freelance.bookCar.services.product.tourService.tourSchedule.TourScheduleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour-schedule")
@CrossOrigin(origins = "*")
@Slf4j
public class TourScheduleController {

    @Autowired
    private TourScheduleService tourScheduleService;

    @PostMapping()
    public ResponseEntity<ApiResponse<CreateTourScheduleResponse>> create(@ModelAttribute @Valid CreateTourScheduleRequest createTourScheduleRequest) {
            CreateTourScheduleResponse response = tourScheduleService.createTourSchedule(createTourScheduleRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tour schedule created successfully", response));
    }

    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateTourScheduleResponse>> update(@ModelAttribute @Valid UpdateTourScheduleRequest updateTourScheduleRequest) {
            UpdateTourScheduleResponse response = tourScheduleService.updateTourSchedule(updateTourScheduleRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tour schedule updated successfully", response));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<GetTourScheduleResponse>> getById(@RequestParam Integer id) {
            GetTourScheduleResponse response = tourScheduleService.findById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tour schedule retrieved successfully", response));
    }

    @GetMapping("/tour")
    public ResponseEntity<ApiResponse<List<GetTourScheduleResponse>>> getByIdTour(@RequestParam Integer idTour) {
        List<GetTourScheduleResponse> response = tourScheduleService.findAllByIdTour(idTour);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tour schedule retrieved successfully", response));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<GetTourScheduleResponse>>> getAll() {
        List<GetTourScheduleResponse> response = tourScheduleService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Tour schedule retrieved successfully", response));
    }

}