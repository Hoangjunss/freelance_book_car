package com.freelance.bookCar.controller.product.ticket;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.product.ticketDTO.tourism.CreateTourismRequest;
import com.freelance.bookCar.dto.request.product.ticketDTO.tourism.UpdateTourismRequest;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotel.GetHotelResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.CreateTourismResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.GetTourismDetailResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.GetTourismResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.UpdateTourismResponse;
import com.freelance.bookCar.services.product.ticketService.tourism.TourismService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tourism")
@CrossOrigin(origins = "*")
@Slf4j
public class TourismController {

    @Autowired
    private TourismService tourismService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<ApiResponse<CreateTourismResponse>> create(@ModelAttribute @Valid CreateTourismRequest createTourismRequest) {
            CreateTourismResponse response = tourismService.createTourism(createTourismRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tourism entity created successfully", response));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateTourismResponse>> update(@ModelAttribute @Valid UpdateTourismRequest updateTourismRequest) {
            UpdateTourismResponse response = tourismService.updateTourism(updateTourismRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tourism entity updated successfully", response));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<GetTourismResponse>> getById(@RequestParam Integer id) {
            GetTourismResponse response = tourismService.findById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tourism entity retrieved successfully", response));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping()
    public ResponseEntity<ApiResponse<List<GetTourismResponse>>> getAll() {
        List<GetTourismResponse> response = tourismService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Hotel retrieved successfully", response));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{location}")
    public ResponseEntity<ApiResponse<List<GetTourismResponse>>> getLocation(@PathVariable String location) {
        List<GetTourismResponse> response = tourismService.findLocation(location);
        return ResponseEntity.ok(new ApiResponse<>(true, "Hotel retrieved successfully", response));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<GetTourismDetailResponse>> getDetail(@RequestParam Integer id, @RequestParam(required = false)  LocalDateTime dateTime) {
        if(dateTime==null){
            dateTime=LocalDateTime.now();
        }
        GetTourismDetailResponse response=tourismService.getDetail(id,dateTime);
        return ResponseEntity.ok(new ApiResponse<>(true, "Hotel retrieved successfully", response));
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<GetTourismResponse>>> getSearch(@RequestParam String name) {
        List<GetTourismResponse> response = tourismService.findById(name);
        return ResponseEntity.ok(new ApiResponse<>(true, "Hotel retrieved successfully", response));
    }
}