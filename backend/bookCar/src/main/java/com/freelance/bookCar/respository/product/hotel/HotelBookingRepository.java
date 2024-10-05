package com.freelance.bookCar.respository.product.hotel;

import com.freelance.bookCar.models.product.hotel.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HotelBookingRepository extends JpaRepository<HotelBooking,Integer> {
    HotelBooking findByIdAndStartDate(Integer id, LocalDateTime date);

    List<HotelBooking> findAllByHotel(Integer id);
}
