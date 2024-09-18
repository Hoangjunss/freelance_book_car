package com.freelance.bookCar.services.booking;

import com.freelance.bookCar.dto.request.booking.CreateBookingRequest;
import com.freelance.bookCar.dto.request.booking.UpdateBookingRequest;
import com.freelance.bookCar.dto.response.product.booking.CreateBookingResponse;
import com.freelance.bookCar.dto.response.product.booking.GetBookingResponse;
import com.freelance.bookCar.dto.response.product.booking.UpdateBookingResponse;
import com.freelance.bookCar.respository.booking.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService{
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public CreateBookingResponse create(CreateBookingRequest createBookingRequest) {
        return null;
    }

    @Override
    public UpdateBookingResponse update(UpdateBookingRequest updateBookingRequest) {
        return null;
    }

    @Override
    public GetBookingResponse findById(Integer id) {
        return null;
    }
}
