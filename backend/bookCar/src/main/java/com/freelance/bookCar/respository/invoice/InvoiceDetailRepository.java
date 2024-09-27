package com.freelance.bookCar.respository.invoice;

import com.freelance.bookCar.models.invoice.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail,Integer> {
    List<InvoiceDetail> findByMonthAndHotel(int month);
    List<InvoiceDetail> findByMonthAndTour(int month);
    List<InvoiceDetail> findByMonthAndTourism(int month);
    List<InvoiceDetail> findByIdInvoiceAndMonthYear(int month,int year);
}
