package com.freelance.bookCar.respository.product.hotel;

import com.freelance.bookCar.models.product.hotel.Hotel;
import com.freelance.bookCar.models.product.hotel.HotelBooking;
import com.freelance.bookCar.models.product.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface HotelBookingRepository extends JpaRepository<HotelBooking,Integer> {
    HotelBooking findByIdAndStartDate(Integer id, LocalDateTime date);
}
