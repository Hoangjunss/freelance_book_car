package com.freelance.bookCar.controller.booking;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.booking.CreateBookingRequest;
import com.freelance.bookCar.dto.request.booking.UpdateBookingRequest;
import com.freelance.bookCar.dto.response.booking.CreateBookingResponse;
import com.freelance.bookCar.dto.response.booking.GetBookingResponse;
import com.freelance.bookCar.dto.response.booking.UpdateBookingResponse;
import com.freelance.bookCar.services.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateBookingResponse>> create(@RequestBody CreateBookingRequest createBookingRequest){
        CreateBookingResponse createBookingResponse=bookingService.create(createBookingRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", createBookingResponse));
    }

    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateBookingResponse>> update(@RequestBody UpdateBookingRequest updateBookingRequest){
        UpdateBookingResponse updateBookingResponse =  bookingService.update(updateBookingRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking updated successfully", updateBookingResponse));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<GetBookingResponse>> getById(@RequestParam Integer id){
        GetBookingResponse GetBookingResponse = bookingService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking retrieved successfully", GetBookingResponse));
    }

}
