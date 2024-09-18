package com.freelance.bookCar.respository.booking;

import com.freelance.bookCar.models.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
