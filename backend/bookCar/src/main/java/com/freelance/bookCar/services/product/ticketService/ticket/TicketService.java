package com.freelance.bookCar.services.product.ticketService.ticket;

import com.freelance.bookCar.dto.request.product.ticketDTO.ticket.CreateTicketRequest;
import com.freelance.bookCar.dto.request.product.ticketDTO.ticket.UpdateTicketRequest;
import com.freelance.bookCar.dto.response.product.ticketDTO.ticket.CreateTicketResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.ticket.GetTicketResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.ticket.UpdateTicketResponse;

public interface TicketService {
    CreateTicketResponse createTicket(CreateTicketRequest createTicketRequest);
    UpdateTicketResponse updateTicket(UpdateTicketRequest updateTicketRequest);
    GetTicketResponse findById(Integer id);
}
