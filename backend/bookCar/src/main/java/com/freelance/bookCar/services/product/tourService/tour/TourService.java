package com.freelance.bookCar.services.product.tourService.tour;

import com.freelance.bookCar.dto.request.product.tourDTO.tour.CreateTourRequest;
import com.freelance.bookCar.dto.request.product.tourDTO.tour.UpdateTourRequest;
import com.freelance.bookCar.dto.response.product.tourDTO.tour.CreateTourResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tour.GetTourResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tour.UpdateTourResponse;
import org.springframework.stereotype.Service;

@Service
public interface TourService {
    CreateTourResponse createTour(CreateTourRequest createTourRequest);
    UpdateTourResponse updateTour(UpdateTourRequest updateTourRequest);
    GetTourResponse findById(Integer id);
}
