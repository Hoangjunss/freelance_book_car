package com.freelance.bookCar.services.product.ticketService.ticket;

import com.freelance.bookCar.dto.request.product.ticketDTO.ticket.CreateTicketRequest;
import com.freelance.bookCar.dto.request.product.ticketDTO.ticket.UpdateTicketRequest;
import com.freelance.bookCar.dto.response.product.ticketDTO.ticket.CreateTicketResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.ticket.GetTicketResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.ticket.UpdateTicketResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.GetTourScheduleResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.product.ticket.Ticket;
import com.freelance.bookCar.respository.product.ticket.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CreateTicketResponse createTicket(CreateTicketRequest createTicketRequest) {
        log.info("Create ticket");

        if (createTicketRequest.getStartDate() == null) {
            throw new CustomException(Error.TICKET_INVALID_START_DATE);
        }

        if (createTicketRequest.getTourPrice() < 0D) {
            throw new CustomException(Error.TICKET_INVALID_TOUR_PRICE);
        }
        if (createTicketRequest.getIdTourism() == null) {
            throw new CustomException(Error.TICKET_INVALID_ID_TOURISM);
        }

        // Create new Ticket object
        Ticket ticketSave = Ticket.builder()
                .id(getGenerationId())
                .startDate(createTicketRequest.getStartDate())
                .tourPrice(createTicketRequest.getTourPrice())
                .idTourism(createTicketRequest.getIdTourism())
                .build();

        try {
            return modelMapper.map(ticketRepository.save(ticketSave), CreateTicketResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while saving Ticket: {}", e.getMessage(), e);
            throw new CustomException(Error.TICKET_UNABLE_TO_SAVE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public UpdateTicketResponse updateTicket(UpdateTicketRequest updateTicketRequest) {
        log.info("Update ticket by id: {}", updateTicketRequest.getId());

        if (updateTicketRequest.getId() == null) {
            throw new CustomException(Error.TICKET_NOT_FOUND);
        }

        Ticket existingTicket = modelMapper.map(findById(updateTicketRequest.getId()), Ticket.class);

        if (updateTicketRequest.getStartDate() != null) {
            existingTicket.setStartDate(updateTicketRequest.getStartDate());
        }
        if (updateTicketRequest.getTourPrice() >= 0D) {
            existingTicket.setTourPrice(updateTicketRequest.getTourPrice());
        }
        if(updateTicketRequest.getIdTourism() != null) {
            existingTicket.setIdTourism(updateTicketRequest.getIdTourism());
        }

        try {
            return modelMapper.map(ticketRepository.save(existingTicket), UpdateTicketResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while updating Ticket: {}", e.getMessage(), e);
            throw new CustomException(Error.TICKET_UNABLE_TO_UPDATE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public GetTicketResponse findById(Integer id) {
        log.info("Find ticket by id: {}", id);
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.TICKET_NOT_FOUND));

        return modelMapper.map(ticket, GetTicketResponse.class);
    }

    @Override
    public Ticket findByIdAndByStartDate(Integer id, LocalDateTime date) {
        return ticketRepository.findByIdAndStartDate(id,date);
    }

    @Override
    public List<GetTicketResponse> findAllbyIdTourism(Integer id) {
        return ticketRepository.findAllByIdTourism(id)
                .stream().map(
                        tourSchedule -> modelMapper.map(tourSchedule, GetTicketResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<GetTicketResponse> findAllTickets() {
        return ticketRepository.findAll().stream().map(ticket -> modelMapper.map(ticket, GetTicketResponse.class)).collect(Collectors.toList());
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}

