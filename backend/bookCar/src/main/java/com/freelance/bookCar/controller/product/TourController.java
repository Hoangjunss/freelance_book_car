package com.freelance.bookCar.controller.product;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.product.tour.CreateTourRequest;
import com.freelance.bookCar.dto.request.product.tour.UpdateTourRequest;
import com.freelance.bookCar.dto.response.product.tour.CreateTourResponse;
import com.freelance.bookCar.dto.response.product.tour.GetTourResponse;
import com.freelance.bookCar.dto.response.product.tour.UpdateTourResponse;
import com.freelance.bookCar.services.product.tour.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tour")
public class TourController {
    @Autowired
    private TourService tourService;
    @PostMapping()
    public ResponseEntity<ApiResponse<CreateTourResponse>> create(@RequestBody CreateTourRequest createTourRequest){
        CreateTourResponse createTourResponse=tourService.createTour(createTourRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tour created successfully", createTourResponse));
    }
    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateTourResponse>> update(@RequestBody UpdateTourRequest updateTourRequest){
        UpdateTourResponse updateTourResponse=tourService.updateTour(updateTourRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tour update successfully", updateTourResponse));
    }
    @GetMapping()
    public ResponseEntity<ApiResponse<GetTourResponse>> getTour(@RequestParam Integer id){
        GetTourResponse getTourResponse=tourService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Id Tour  successfully", getTourResponse));
    }


}
