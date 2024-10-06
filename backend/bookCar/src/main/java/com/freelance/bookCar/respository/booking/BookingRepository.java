package com.freelance.bookCar.respository.booking;

import com.freelance.bookCar.models.booking.Booking;
import com.freelance.bookCar.models.booking.TypeBooking;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByTypeBooking(TypeBooking typeBooking);

    Booking findByIdUser(Integer idUser);

    Booking findByTypeBookingAndIdUser(TypeBooking status, Integer idUser);

    List<Booking> findAllByIdUser(Integer id);

    List<Booking> findAllByTypeBookingNotAndIdUser(TypeBooking typeBooking, Integer idUser);

    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.totalPrice = (SELECT SUM(bd.totalPrice) FROM BookingDetail bd WHERE bd.idBooking = b.id) WHERE b.id = :idBooking")
    void updateTotalPrice(@Param("idBooking") Integer idBooking);

}
