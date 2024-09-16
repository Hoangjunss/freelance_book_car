package com.freelance.bookCar.services.product.ticketService.tourism;

import com.freelance.bookCar.dto.request.product.ticketDTO.tourism.CreateTourismRequest;
import com.freelance.bookCar.dto.request.product.ticketDTO.tourism.UpdateTourismRequest;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.CreateTourismResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.GetTourismResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.UpdateTourismResponse;

public interface TourismService {
    CreateTourismResponse createTourism(CreateTourismRequest createTourismRequest);
    UpdateTourismResponse updateTourism(UpdateTourismRequest updateTourismRequest);
    GetTourismResponse findById(Integer id);

}
