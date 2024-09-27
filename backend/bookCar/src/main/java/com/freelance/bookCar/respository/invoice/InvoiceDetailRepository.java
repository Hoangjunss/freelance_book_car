package com.freelance.bookCar.respository.invoice;

import com.freelance.bookCar.models.invoice.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail,Integer> {


    @Query(value = "SELECT id.* FROM invoice_detail id JOIN invoice i ON id.id_invoice = i.id WHERE id.id_hotel = :idHotel AND MONTH(i.invoice_date) = :month", nativeQuery = true)
    List<InvoiceDetail> findByMonthAndHotel(@Param("month") int month, @Param("idHotel") int idHotel);

    @Query(value = "SELECT id.* FROM invoice_detail id JOIN invoice i ON id.id_invoice = i.id WHERE id.id_tour = :idTour AND MONTH(i.invoice_date) = :month", nativeQuery = true)
    List<InvoiceDetail> findByMonthAndTour(@Param("month") int month, @Param("idTour") int idTour);

    @Query(value = "SELECT id.* FROM invoice_detail id JOIN invoice i ON id.id_invoice = i.id WHERE id.id_tourism = :idTourism AND MONTH(i.invoice_date) = :month", nativeQuery = true)
    List<InvoiceDetail> findByMonthAndTourism(@Param("month") int month, @Param("idTourism") int idTourism);

}
