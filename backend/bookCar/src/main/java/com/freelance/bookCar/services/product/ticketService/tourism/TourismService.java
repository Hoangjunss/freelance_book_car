package com.freelance.bookCar.services.product.ticketService.tourism;

import com.freelance.bookCar.dto.request.product.ticketDTO.tourism.CreateTourismRequest;
import com.freelance.bookCar.dto.request.product.ticketDTO.tourism.UpdateTourismRequest;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.CreateTourismResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.GetTourismDetailResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.GetTourismResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.UpdateTourismResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface TourismService {
    CreateTourismResponse createTourism(CreateTourismRequest createTourismRequest);
    UpdateTourismResponse updateTourism(UpdateTourismRequest updateTourismRequest);
    GetTourismResponse findById(Integer id);
    List<GetTourismResponse> getAll();
    List<GetTourismResponse> findLocation(String location);
    GetTourismDetailResponse getDetail(Integer id, LocalDateTime date);

}
