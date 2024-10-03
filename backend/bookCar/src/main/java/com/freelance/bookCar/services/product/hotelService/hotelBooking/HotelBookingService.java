package com.freelance.bookCar.services.product.hotelService.hotelBooking;

import com.freelance.bookCar.dto.request.product.hotelDTO.hotelBooking.CreateHotelBookingRequest;
import com.freelance.bookCar.dto.request.product.hotelDTO.hotelBooking.UpdateHotelBookingRequest;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking.CreateHotelBookingResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking.GetHotelBookingResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking.UpdateHotelBookingResponse;

import java.util.List;

public interface HotelBookingService {
    CreateHotelBookingResponse createHotelBooking(CreateHotelBookingRequest createHotelBookingRequest);
    UpdateHotelBookingResponse updateHotelBooking(UpdateHotelBookingRequest updateHotelBookingRequest);
    GetHotelBookingResponse findById(Integer id);
    List<GetHotelBookingResponse> findAllByIdHotel(Integer idHotel);
    List<GetHotelBookingResponse> getAll();
}
