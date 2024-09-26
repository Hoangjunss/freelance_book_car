package com.freelance.bookCar.controller.product.tour;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.product.tourDTO.tour.CreateTourRequest;
import com.freelance.bookCar.dto.request.product.tourDTO.tour.UpdateTourRequest;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotel.GetHotelResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.GetTourismDetailResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tour.CreateTourResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tour.GetTourDetailResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tour.GetTourResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tour.UpdateTourResponse;
import com.freelance.bookCar.services.product.tourService.tour.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tour")
@CrossOrigin(origins = "*")
public class TourController {
    @Autowired
    private TourService tourService;
    @PostMapping()
    public ResponseEntity<ApiResponse<CreateTourResponse>> create(@ModelAttribute @Valid CreateTourRequest createTourRequest){
        CreateTourResponse createTourResponse=tourService.createTour(createTourRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tour created successfully", createTourResponse));
    }
    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateTourResponse>> update(@ModelAttribute @Valid UpdateTourRequest updateTourRequest){
        UpdateTourResponse updateTourResponse=tourService.updateTour(updateTourRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tour update successfully", updateTourResponse));
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<GetTourResponse>> getTour(@PathVariable Integer id){
        GetTourResponse getTourResponse=tourService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Id Tour  successfully", getTourResponse));
    }
    @GetMapping()
    public ResponseEntity<ApiResponse<List<GetTourResponse>>> getAll() {
        List<GetTourResponse> response = tourService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Hotel retrieved successfully", response));
    }
    @GetMapping("/{location}")
    public ResponseEntity<ApiResponse<List<GetTourResponse>>> getLocation(@PathVariable String location) {
        List<GetTourResponse> response = tourService.getLocation(location);
        return ResponseEntity.ok(new ApiResponse<>(true, "Hotel retrieved successfully", response));
    }
    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<GetTourDetailResponse>> getDetail(@RequestParam Integer id, @RequestParam(required = false) LocalDateTime dateTime) {
        if(dateTime==null){
            dateTime=LocalDateTime.now();
        }
        GetTourDetailResponse response=tourService.getDetail(id,dateTime);
        return ResponseEntity.ok(new ApiResponse<>(true, "Hotel retrieved successfully", response));
    }


}
