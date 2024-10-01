package com.freelance.bookCar.controller.product.tour;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.product.tourDTO.tourScheduleStatus.CreateTourScheduleStatusRequest;
import com.freelance.bookCar.dto.request.product.tourDTO.tourScheduleStatus.UpdateTourScheduleStatusRequest;
import com.freelance.bookCar.dto.response.product.tourDTO.tourScheduleStatus.CreateTourScheduleStatusResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourScheduleStatus.GetTourScheduleStatusResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourScheduleStatus.UpdateTourScheduleStatusResponse;
import com.freelance.bookCar.services.product.tourService.tourScheduleStatus.TourScheduleStatusService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tour-schedule-status")
@CrossOrigin(origins = "*")
@Slf4j
public class TourScheduleStatusController {

    @Autowired
    private TourScheduleStatusService tourScheduleStatusService;

    // API tạo Tour Schedule Status
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping()
    public ResponseEntity<ApiResponse<CreateTourScheduleStatusResponse>> create(
            @ModelAttribute @Valid CreateTourScheduleStatusRequest createTourScheduleStatusRequest) {
        log.info("Creating Tour Schedule Status");
        CreateTourScheduleStatusResponse response = tourScheduleStatusService.create(createTourScheduleStatusRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tour schedule status created successfully", response));
    }

    // API cập nhật Tour Schedule Status
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateTourScheduleStatusResponse>> update(
            @ModelAttribute @Valid UpdateTourScheduleStatusRequest updateTourScheduleStatusRequest) {
        log.info("Updating Tour Schedule Status with id: {}", updateTourScheduleStatusRequest.getId());
        UpdateTourScheduleStatusResponse response = tourScheduleStatusService.update(updateTourScheduleStatusRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tour schedule status updated successfully", response));
    }

    // API lấy Tour Schedule Status theo id
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping()
    public ResponseEntity<ApiResponse<GetTourScheduleStatusResponse>> getById(@RequestParam Integer id) {
        log.info("Fetching Tour Schedule Status with id: {}", id);
        GetTourScheduleStatusResponse response = tourScheduleStatusService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tour schedule status retrieved successfully", response));
    }
}