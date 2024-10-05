package com.freelance.bookCar.services.booking;

import com.freelance.bookCar.dto.request.booking.CreateBookingRequest;
import com.freelance.bookCar.dto.request.booking.OrderRequest;
import com.freelance.bookCar.dto.request.booking.UpdateBookingRequest;
import com.freelance.bookCar.dto.request.booking.bookingHotel.AddBookingHotelRequest;
import com.freelance.bookCar.dto.request.booking.bookingHotel.UpdateBookingHotelRequest;
import com.freelance.bookCar.dto.request.booking.bookingTour.AddBookingTourRequest;
import com.freelance.bookCar.dto.request.booking.bookingTour.UpdateBookingTourRequest;
import com.freelance.bookCar.dto.request.booking.bookingTourism.AddBookingTourismRequest;
import com.freelance.bookCar.dto.request.booking.bookingTourism.UpdateBookingTourismRequest;
import com.freelance.bookCar.dto.response.booking.CreateBookingResponse;
import com.freelance.bookCar.dto.response.booking.GetBookingResponse;
import com.freelance.bookCar.dto.response.booking.OrderResponse;
import com.freelance.bookCar.dto.response.booking.UpdateBookingResponse;
import com.freelance.bookCar.dto.response.booking.bookingHotel.AddBookingHotelResponse;
import com.freelance.bookCar.dto.response.booking.bookingHotel.UpdateBookingHotelResponse;
import com.freelance.bookCar.dto.response.booking.bookingTour.AddBookingTourResponse;
import com.freelance.bookCar.dto.response.booking.bookingTour.UpdateBookingTourResponse;
import com.freelance.bookCar.dto.response.booking.bookingTourism.AddBookingTourismResponse;
import com.freelance.bookCar.dto.response.booking.bookingTourism.UpdateBookingTourismResponse;
import com.freelance.bookCar.dto.response.bookingDetail.GetBookingDetailResponse;
import com.freelance.bookCar.models.booking.Booking;
import com.freelance.bookCar.models.booking.TypeBooking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    CreateBookingResponse create(CreateBookingRequest createBookingRequest);
    UpdateBookingResponse update(UpdateBookingRequest updateBookingRequest);
    GetBookingResponse findById(Integer id);
    GetBookingResponse findByIdUser(Integer idUser);
    List<GetBookingDetailResponse> findByIdBooking(Integer idBooking);
    AddBookingTourResponse addBookingTour(AddBookingTourRequest addBookingTourRequest);
    AddBookingTourismResponse addBookingTourism(AddBookingTourismRequest addBookingTourismRequest);
    AddBookingHotelResponse addBookingHotel(AddBookingHotelRequest addBookingHotelRequest);
    UpdateBookingTourResponse updateBookingTour(UpdateBookingTourRequest updateBookingTourRequest);
    UpdateBookingTourismResponse updateBookingTourism(UpdateBookingTourismRequest updateBookingTourismRequest);
    UpdateBookingHotelResponse updateBookingHotel(UpdateBookingHotelRequest updateBookingHotelRequest);
    List<GetBookingResponse> getAll();
    List<GetBookingResponse> findType(String type);
    GetBookingResponse updateType(Integer id,String type);
    OrderResponse order(OrderRequest orderRequest);
    List<GetBookingResponse> findId(Integer IdUser);
    void deleteBookingDetail(Integer id);
    List<GetBookingResponse> findAllByTypeBookingNotAndIdUser(Integer idUser);
}
