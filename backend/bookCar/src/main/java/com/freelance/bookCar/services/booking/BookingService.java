package com.freelance.bookCar.services.booking;

import com.freelance.bookCar.dto.request.booking.CreateBookingRequest;
import com.freelance.bookCar.dto.request.booking.UpdateBookingRequest;
import com.freelance.bookCar.dto.response.product.booking.CreateBookingResponse;
import com.freelance.bookCar.dto.response.product.booking.GetBookingResponse;
import com.freelance.bookCar.dto.response.product.booking.UpdateBookingResponse;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {
    CreateBookingResponse create(CreateBookingRequest createBookingRequest);
    UpdateBookingResponse update(UpdateBookingRequest updateBookingRequest);
    GetBookingResponse findById(Integer id);
}