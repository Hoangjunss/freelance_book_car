package com.freelance.bookCar.controller.product.ticket;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.product.ticketDTO.tourism.CreateTourismRequest;
import com.freelance.bookCar.dto.request.product.ticketDTO.tourism.UpdateTourismRequest;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotel.GetHotelResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.CreateTourismResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.GetTourismResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.UpdateTourismResponse;
import com.freelance.bookCar.services.product.ticketService.tourism.TourismService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tourism")
@CrossOrigin(origins = "*")
@Slf4j
public class TourismController {

    @Autowired
    private TourismService tourismService;

    @PostMapping()
    public ResponseEntity<ApiResponse<CreateTourismResponse>> create(@ModelAttribute @Valid CreateTourismRequest createTourismRequest) {
            CreateTourismResponse response = tourismService.createTourism(createTourismRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tourism entity created successfully", response));
    }

    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateTourismResponse>> update(@ModelAttribute @Valid UpdateTourismRequest updateTourismRequest) {
            UpdateTourismResponse response = tourismService.updateTourism(updateTourismRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tourism entity updated successfully", response));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<GetTourismResponse>> getById(@RequestParam Integer id) {
            GetTourismResponse response = tourismService.findById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tourism entity retrieved successfully", response));
    }
    @GetMapping()
    public ResponseEntity<ApiResponse<List<GetTourismResponse>>> getAll() {
        List<GetTourismResponse> response = tourismService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Hotel retrieved successfully", response));
    }
    @GetMapping("/{location}")
    public ResponseEntity<ApiResponse<List<GetTourismResponse>>> getLocation(@PathVariable String location) {
        List<GetTourismResponse> response = tourismService.findLocation(location);
        return ResponseEntity.ok(new ApiResponse<>(true, "Hotel retrieved successfully", response));
    }
}