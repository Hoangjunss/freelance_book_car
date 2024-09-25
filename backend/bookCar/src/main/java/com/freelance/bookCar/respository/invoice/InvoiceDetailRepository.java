package com.freelance.bookCar.respository.invoice;

import com.freelance.bookCar.models.invoice.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail,Integer> {
}
