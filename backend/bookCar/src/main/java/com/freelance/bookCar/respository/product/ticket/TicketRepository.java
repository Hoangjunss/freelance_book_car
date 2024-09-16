package com.freelance.bookCar.respository.product.ticket;

import com.freelance.bookCar.models.product.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {
}
