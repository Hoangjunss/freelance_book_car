package com.freelance.bookCar.services.product.ticketService.ticket;

import com.freelance.bookCar.dto.request.product.ticketDTO.ticket.CreateTicketRequest;
import com.freelance.bookCar.dto.request.product.ticketDTO.ticket.UpdateTicketRequest;
import com.freelance.bookCar.dto.response.product.ticketDTO.ticket.CreateTicketResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.ticket.GetTicketResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.ticket.UpdateTicketResponse;
import com.freelance.bookCar.models.product.ticket.Ticket;
import com.freelance.bookCar.respository.product.ticket.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CreateTicketResponse createTicket(CreateTicketRequest createTicketRequest) {
        if(createTicketRequest.getStartDate()==null){

        }
        if (createTicketRequest.getStartDate()==null){

        }
        Ticket ticket=Ticket.builder()
                .id(getGenerationId())
                .startDate(createTicketRequest.getStartDate())
                .tourPrice(createTicketRequest.getTourPrice())
                .build();
        return modelMapper.map(ticketRepository.save(ticket),CreateTicketResponse.class);
    }
    @Override
    public UpdateTicketResponse updateTicket(UpdateTicketRequest updateTicketRequest) {
        if (updateTicketRequest.getStartDate() == null) {

        }

        Ticket existingTicket = ticketRepository.findById(updateTicketRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
        existingTicket.setStartDate(updateTicketRequest.getStartDate());
        existingTicket.setTourPrice(updateTicketRequest.getTourPrice());


        return modelMapper.map(ticketRepository.save(existingTicket), UpdateTicketResponse.class);
    }

    @Override
    public GetTicketResponse findById(Integer id) {
       Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        return modelMapper.map(ticket, GetTicketResponse.class);
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}

