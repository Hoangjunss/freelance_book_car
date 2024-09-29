package com.freelance.bookCar.controller;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.booking.bookingHotel.AddBookingHotelRequest;
import com.freelance.bookCar.dto.request.page.CreatePageRequest;
import com.freelance.bookCar.dto.response.booking.bookingHotel.AddBookingHotelResponse;
import com.freelance.bookCar.dto.response.page.CreatePageReponse;
import com.freelance.bookCar.dto.response.page.GetPageResponse;
import com.freelance.bookCar.services.page.PageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/page")
@CrossOrigin(origins = "*")
public class PageController {
    @Autowired
    private PageService pageService;
    @PostMapping()
    public ResponseEntity<ApiResponse<CreatePageReponse>> create(@ModelAttribute @Valid CreatePageRequest addBookingTourRequest){
        CreatePageReponse addBookingTourResponse=pageService.page(addBookingTourRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", addBookingTourResponse));
    }
    @GetMapping("/home")
    public ResponseEntity<ApiResponse<GetPageResponse>> getHome(){
        GetPageResponse addBookingTourResponse=pageService.pageHome();
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", addBookingTourResponse));
    }
    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<List<GetPageResponse>>> getDetail(){
        List<GetPageResponse> addBookingTourResponse=pageService.pageDetail();
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", addBookingTourResponse));
    }
    @GetMapping("/footer")
    public ResponseEntity<ApiResponse<GetPageResponse>> getFooter(){
        GetPageResponse addBookingTourResponse=pageService.pageFooter();
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", addBookingTourResponse));
    }
}
