package com.freelance.bookCar.respository.booking;

import com.freelance.bookCar.models.booking.BookingDetail;
import com.freelance.bookCar.models.invoice.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingDetailRepository extends JpaRepository<BookingDetail,Integer> {
    @Query("SELECT SUM(bd.totalPrice) FROM BookingDetail bd WHERE bd.idBooking = :idBooking")
    Double sumTotalPriceByBookingId(@Param("idBooking") Integer idBooking);

    List<BookingDetail> findAllByIdBooking(Integer idBooking);
    @Query(value = "select invoice_detail.* from invoice inner join invoice_detail on invoice.id = invoice_detail.id_invoice where YEAR(invoice.invoice_date) = :year AND MONTH(invoice.invoice_date) = :month AND invoice_detail.id NOT IN (select invoice_detail.id from invoice_detail where invoice_detail.id_hotel IS NULL);", nativeQuery = true)
    BookingDetail findByIsHotel(@Param("id") Integer idBooking);

    @Query(value = "select invoice_detail.* from invoice inner join invoice_detail on invoice.id = invoice_detail.id_invoice where YEAR(invoice.invoice_date) = :year AND MONTH(invoice.invoice_date) = :month AND invoice_detail.id NOT IN (select invoice_detail.id from invoice_detail where invoice_detail.id_tour IS NULL);", nativeQuery = true)
    BookingDetail findByIsTour(@Param("id") Integer idBooking);

    @Query(value = "select invoice_detail.* from invoice inner join invoice_detail on invoice.id = invoice_detail.id_invoice where YEAR(invoice.invoice_date) = :year AND MONTH(invoice.invoice_date) = :month AND invoice_detail.id NOT IN (select invoice_detail.id from invoice_detail where invoice_detail.id_tourism IS NULL);", nativeQuery = true)
    BookingDetail findByIsTourism(@Param("id") Integer idBooking);
}
