package com.freelance.bookCar.respository.product.ticket;

import com.freelance.bookCar.models.product.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {

    Ticket findByIdAndStartDate(Integer id, LocalDateTime date);

    List<Ticket> findAllByIdTourism(Integer id);
}
