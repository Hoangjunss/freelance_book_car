package com.freelance.bookCar.respository.product.ticket;

import com.freelance.bookCar.models.product.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    Ticket findByIdAndByStartDate(Integer id, LocalDateTime date);
}
