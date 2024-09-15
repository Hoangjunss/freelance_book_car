package com.freelance.bookCar.services.product.tour;

import com.freelance.bookCar.dto.request.product.tour.CreateTourRequest;
import com.freelance.bookCar.dto.request.product.tour.FilterTourRequest;
import com.freelance.bookCar.dto.request.product.tour.UpdateTourRequest;
import com.freelance.bookCar.dto.response.product.tour.CreateTourResponse;
import com.freelance.bookCar.dto.response.product.tour.GetTourResponse;
import com.freelance.bookCar.dto.response.product.tour.UpdateTourResponse;

import java.util.List;

public interface TourService {
    CreateTourResponse createTour(CreateTourRequest createTourRequest);
    UpdateTourResponse updateTour(UpdateTourRequest updateTourRequest);
    GetTourResponse findById(Integer id);
    List<GetTourResponse> filterTour(FilterTourRequest filterTourRequest);
}
