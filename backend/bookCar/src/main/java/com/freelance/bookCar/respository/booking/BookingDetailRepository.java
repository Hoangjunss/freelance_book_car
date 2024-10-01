package com.freelance.bookCar.respository.booking;

import com.freelance.bookCar.models.booking.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingDetailRepository extends JpaRepository<BookingDetail,Integer> {
    @Query("SELECT SUM(bd.totalPrice) FROM BookingDetail bd WHERE bd.idBooking = :idBooking")
    Double sumTotalPriceByBookingId(@Param("idBooking") Integer idBooking);

    List<BookingDetail> findAllByIdBooking(Integer idBooking);

    @Query(value = "select * from booking_detail bd where bd.id_booking = :id AND bd.id_hotel IN (select id_hotel from booking_detail);", nativeQuery = true)
    BookingDetail findByIsHotel(@Param("id") Integer idBooking);

    @Query(value = "select * from booking_detail bd where bd.id_booking = :id AND bd.id_tour IN (select id_tour from booking_detail);", nativeQuery = true)
    BookingDetail findByIsTour(@Param("id") Integer idBooking);

    @Query(value = "select * from booking_detail bd where bd.id_booking = :id AND bd.id_ticket IN (select id_ticket from booking_detail);", nativeQuery = true)
    BookingDetail findByIsTourism(@Param("id") Integer idBooking);
}
