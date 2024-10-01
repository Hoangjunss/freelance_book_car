package com.freelance.bookCar.controller.product.hotel;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.product.hotelDTO.hotel.CreateHotelRequest;
import com.freelance.bookCar.dto.request.product.hotelDTO.hotel.UpdateHotelRequest;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotel.CreateHotelResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotel.GetHotelResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotel.UpdateHotelResponse;
import com.freelance.bookCar.services.product.hotelService.hotel.HotelService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotel")
@CrossOrigin(origins = "*")
@Slf4j
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<ApiResponse<CreateHotelResponse>> create(@ModelAttribute @Valid CreateHotelRequest createHotelRequest) {
            CreateHotelResponse response = hotelService.createHotel(createHotelRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Hotel created successfully", response));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateHotelResponse>> update(@ModelAttribute @Valid UpdateHotelRequest updateHotelRequest) {
            UpdateHotelResponse response = hotelService.updateHotel(updateHotelRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Hotel updated successfully", response));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<GetHotelResponse>> getById(@PathVariable Integer id) {
            GetHotelResponse response = hotelService.findById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Hotel retrieved successfully", response));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping()
    public ResponseEntity<ApiResponse<List<GetHotelResponse>>> getAll() {
        List<GetHotelResponse> response = hotelService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Hotel retrieved successfully", response));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{location}")
    public ResponseEntity<ApiResponse<List<GetHotelResponse>>> getLocation(@PathVariable String location) {
        List<GetHotelResponse> response = hotelService.findByLocation(location);
        return ResponseEntity.ok(new ApiResponse<>(true, "Hotel retrieved successfully", response));
    }


}