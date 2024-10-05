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

    BookingDetail findByIdBookingAndIdHotel(Integer idBooking,Integer idHotel);

    BookingDetail findByIdBookingAndIdTour(Integer idBooking, Integer idTour);

    BookingDetail findByIdBookingAndIdTicket(Integer idBooking, Integer idTicket);
}
