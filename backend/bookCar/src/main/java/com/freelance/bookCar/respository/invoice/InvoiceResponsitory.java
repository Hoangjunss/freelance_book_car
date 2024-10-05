package com.freelance.bookCar.respository.invoice;

import com.freelance.bookCar.models.invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceResponsitory extends JpaRepository<Invoice, Integer> {
}
