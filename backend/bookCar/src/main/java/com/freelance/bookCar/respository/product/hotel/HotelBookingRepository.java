package com.freelance.bookCar.respository.product.hotel;

import com.freelance.bookCar.models.product.hotel.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelBookingRepository extends JpaRepository<HotelBooking,Integer> {
}
