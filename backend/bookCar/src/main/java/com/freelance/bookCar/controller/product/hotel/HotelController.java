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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
@CrossOrigin(origins = "*")
@Slf4j
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping()
    public ResponseEntity<ApiResponse<CreateHotelResponse>> create(@ModelAttribute @Valid CreateHotelRequest createHotelRequest) {
            CreateHotelResponse response = hotelService.createHotel(createHotelRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Hotel created successfully", response));
    }

    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateHotelResponse>> update(@ModelAttribute @Valid UpdateHotelRequest updateHotelRequest) {
            UpdateHotelResponse response = hotelService.updateHotel(updateHotelRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Hotel updated successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetHotelResponse>> getById(@RequestParam Integer id) {
            GetHotelResponse response = hotelService.findById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Hotel retrieved successfully", response));
    }
    @GetMapping()
    public ResponseEntity<ApiResponse<List<GetHotelResponse>>> getAll() {
        List<GetHotelResponse> response = hotelService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Hotel retrieved successfully", response));
    }
}