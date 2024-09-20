package com.freelance.bookCar.controller.product.hotel;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.product.hotelDTO.hotelBooking.CreateHotelBookingRequest;
import com.freelance.bookCar.dto.request.product.hotelDTO.hotelBooking.UpdateHotelBookingRequest;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking.CreateHotelBookingResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking.GetHotelBookingResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking.UpdateHotelBookingResponse;
import com.freelance.bookCar.services.product.hotelService.hotelBooking.HotelBookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel-booking")
@Slf4j
public class HotelBookingController {

    @Autowired
    private HotelBookingService hotelBookingService;

    @PostMapping()
    public ResponseEntity<ApiResponse<CreateHotelBookingResponse>> create(@RequestBody CreateHotelBookingRequest createHotelBookingRequest) {
            CreateHotelBookingResponse response = hotelBookingService.createHotelBooking(createHotelBookingRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Hotel booking created successfully", response));
    }

    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateHotelBookingResponse>> update(@RequestBody UpdateHotelBookingRequest updateHotelBookingRequest) {
            UpdateHotelBookingResponse response = hotelBookingService.updateHotelBooking(updateHotelBookingRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Hotel booking updated successfully", response));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<GetHotelBookingResponse>> getById(@RequestParam Integer id) {
            GetHotelBookingResponse response = hotelBookingService.findById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Hotel booking retrieved successfully", response));
    }
}