package com.freelance.bookCar.respository.invoice;

import com.freelance.bookCar.models.invoice.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail,Integer> {

    //
    @Query(value = "select invoice_detail.* from invoice inner join invoice_detail on invoice.id = invoice_detail.id_invoice where MONTH(invoice.invoice_date) = :month AND YEAR(invoice.invoice_date) = :year AND invoice_detail.id_hotel IS NOT NULL;", nativeQuery = true)
    List<InvoiceDetail> findByMonthAndHotel(@Param("month") int month, @Param("year") int year);

    @Query(value = "select invoice_detail.* from invoice inner join invoice_detail on invoice.id = invoice_detail.id_invoice where MONTH(invoice.invoice_date) = :month AND YEAR(invoice.invoice_date) = :year AND invoice_detail.id_tour IS NOT NULL;", nativeQuery = true)
    List<InvoiceDetail> findByMonthAndTour(@Param("month") int month, @Param("year") int year);

    @Query(value = "select invoice_detail.* from invoice inner join invoice_detail on invoice.id = invoice_detail.id_invoice where MONTH(invoice.invoice_date) = :month AND YEAR(invoice.invoice_date) = :year AND invoice_detail.id_tourism IS NOT NULL;", nativeQuery = true)
    List<InvoiceDetail> findByMonthAndTourism(@Param("month") int month, @Param("year") int year);

    //
    @Query(value = "select invoice_detail.* from invoice inner join invoice_detail on invoice.id = invoice_detail.id_invoice where YEAR(invoice.invoice_date) = :year AND MONTH(invoice.invoice_date) = :month AND invoice_detail.id NOT IN (select invoice_detail.id from invoice_detail where invoice_detail.id_hotel IS NULL);", nativeQuery = true)
    List<InvoiceDetail> findByMonthAndIsHotel(@Param("year") int year, @Param("month") int month);

    @Query(value = "select invoice_detail.* from invoice inner join invoice_detail on invoice.id = invoice_detail.id_invoice where YEAR(invoice.invoice_date) = :year AND MONTH(invoice.invoice_date) = :month AND invoice_detail.id NOT IN (select invoice_detail.id from invoice_detail where invoice_detail.id_tour IS NULL);", nativeQuery = true)
    List<InvoiceDetail> findByMonthAndIsTour(@Param("year") int year, @Param("month") int month);

    @Query(value = "select invoice_detail.* from invoice inner join invoice_detail on invoice.id = invoice_detail.id_invoice where YEAR(invoice.invoice_date) = :year AND MONTH(invoice.invoice_date) = :month AND invoice_detail.id NOT IN (select invoice_detail.id from invoice_detail where invoice_detail.id_tourism IS NULL);", nativeQuery = true)
    List<InvoiceDetail> findByMonthAndIsTourism(@Param("year") int year, @Param("month") int month);

    //
    @Query(value = "SELECT invoice_detail.* " +
            "FROM invoice " +
            "INNER JOIN invoice_detail ON invoice.id = invoice_detail.id_invoice " +
            "WHERE DATE(invoice.invoice_date) = CURDATE() " +
            "AND invoice_detail.id_hotel IS NOT NULL;",
            nativeQuery = true)
    List<InvoiceDetail> findByTodayAndIsHotel();

    @Query(value = "SELECT invoice_detail.* " +
            "FROM invoice " +
            "INNER JOIN invoice_detail ON invoice.id = invoice_detail.id_invoice " +
            "WHERE DATE(invoice.invoice_date) = CURDATE() " +
            "AND invoice_detail.id_tour IS NOT NULL;",
            nativeQuery = true)
    List<InvoiceDetail> findByTodayAndIsTour();

    @Query(value = "SELECT invoice_detail.* " +
            "FROM invoice " +
            "INNER JOIN invoice_detail ON invoice.id = invoice_detail.id_invoice " +
            "WHERE DATE(invoice.invoice_date) = CURDATE() " +
            "AND invoice_detail.id_tourism IS NOT NULL;",
            nativeQuery = true)
    List<InvoiceDetail> findByTodayAndIsTourism();

    @Query(value = " select invoice_detail.* from invoice inner join invoice_detail on invoice.id = invoice_detail.id_invoice where YEAR(invoice.invoice_date) = :year AND MONTH(invoice.invoice_date) = :month;", nativeQuery = true)
    List<InvoiceDetail> findByIdInvoiceAndMonthYear(@Param("year") int year, @Param("month") int month);

}
