package com.freelance.bookCar.services.product.hotelService.hotel;

import com.freelance.bookCar.dto.request.product.hotelDTO.hotel.CreateHotelRequest;
import com.freelance.bookCar.dto.request.product.hotelDTO.hotel.UpdateHotelRequest;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotel.CreateHotelResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotel.GetHotelResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotel.UpdateHotelResponse;

public interface HotelService {
    CreateHotelResponse createHotel(CreateHotelRequest createHotelRequest);
    UpdateHotelResponse updateHotel(UpdateHotelRequest updateHotelRequest);
    GetHotelResponse findById(Integer id);
}
