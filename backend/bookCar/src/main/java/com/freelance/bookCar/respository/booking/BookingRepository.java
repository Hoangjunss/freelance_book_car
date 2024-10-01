package com.freelance.bookCar.respository.booking;

import com.freelance.bookCar.models.booking.Booking;
import com.freelance.bookCar.models.booking.TypeBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByTypeBooking(TypeBooking typeBooking);

    Booking findByIdUser(Integer idUser);

    Booking findByTypeBookingAndIdUser(TypeBooking status, Integer idUser);
}
