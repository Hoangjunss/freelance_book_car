package com.freelance.bookCar.respository.product.invoice;

import com.freelance.bookCar.models.invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceResponsitory extends JpaRepository<Invoice, Integer> {
}
