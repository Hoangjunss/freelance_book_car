package com.freelance.bookCar.services.product.tourService.tour;

import com.freelance.bookCar.dto.request.product.tourDTO.tour.CreateTourRequest;
import com.freelance.bookCar.dto.request.product.tourDTO.tour.UpdateTourRequest;
import com.freelance.bookCar.dto.response.product.tourDTO.tour.CreateTourResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tour.GetTourDetailResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tour.GetTourResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tour.UpdateTourResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface TourService {
    CreateTourResponse createTour(CreateTourRequest createTourRequest);
    UpdateTourResponse updateTour(UpdateTourRequest updateTourRequest);
    GetTourResponse findById(Integer id);
    List<GetTourResponse> getAll();
    List<GetTourResponse> getLocation(String location);
    GetTourDetailResponse getDetail(Integer id, LocalDateTime dateTime);
}
