package com.freelance.bookCar.controller.product.tour;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.product.tourDTO.tourStatus.CreateTourStatusRequest;
import com.freelance.bookCar.dto.request.product.tourDTO.tourStatus.UpdateTourStatusRequest;
import com.freelance.bookCar.dto.response.product.tourDTO.tourStatus.CreateTourStatusResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourStatus.GetTourStatusResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourStatus.UpdateTourStatusResponse;
import com.freelance.bookCar.services.product.tourService.tourStatus.TourStatusService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tour-status")
@CrossOrigin(origins = "*")
@Slf4j
public class TourStatusController {

    @Autowired
    private TourStatusService tourStatusService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<ApiResponse<CreateTourStatusResponse>> create(@ModelAttribute @Valid CreateTourStatusRequest createTourStatusRequest) {
        log.info("Creating tour status");
        CreateTourStatusResponse response = tourStatusService.create(createTourStatusRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tour status created successfully", response));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateTourStatusResponse>> update(@ModelAttribute @Valid UpdateTourStatusRequest updateTourStatusRequest) {
        log.info("Updating tour status with id: {}", updateTourStatusRequest.getId());
        UpdateTourStatusResponse response = tourStatusService.update(updateTourStatusRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tour status updated successfully", response));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping()
    public ResponseEntity<ApiResponse<GetTourStatusResponse>> getById(@RequestParam Integer id) {
        log.info("Fetching tour status by id: {}", id);
        GetTourStatusResponse response = tourStatusService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tour status retrieved successfully", response));
    }
}