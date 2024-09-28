package com.freelance.bookCar.controller;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.booking.bookingHotel.AddBookingHotelRequest;
import com.freelance.bookCar.dto.request.page.CreatePageRequest;
import com.freelance.bookCar.dto.response.booking.bookingHotel.AddBookingHotelResponse;
import com.freelance.bookCar.dto.response.page.CreatePageReponse;
import com.freelance.bookCar.services.page.PageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class PageController {
    @Autowired
    private PageService pageService;
    @PostMapping("/hotel")
    public ResponseEntity<ApiResponse<CreatePageReponse>> createPage(@ModelAttribute @Valid CreatePageRequest addBookingTourRequest){
        CreatePageReponse addBookingTourResponse=pageService.page(addBookingTourRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", addBookingTourResponse));
    }
}
