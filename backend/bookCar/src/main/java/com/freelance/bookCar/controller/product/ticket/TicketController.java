package com.freelance.bookCar.controller.product.ticket;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.product.ticketDTO.ticket.CreateTicketRequest;
import com.freelance.bookCar.dto.request.product.ticketDTO.ticket.UpdateTicketRequest;
import com.freelance.bookCar.dto.response.product.ticketDTO.ticket.CreateTicketResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.ticket.GetTicketResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.ticket.UpdateTicketResponse;
import com.freelance.bookCar.services.product.ticketService.ticket.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
@CrossOrigin(origins = "*")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping()
    public ResponseEntity<ApiResponse<CreateTicketResponse>> create(@ModelAttribute @Valid CreateTicketRequest createTicketRequest){
        CreateTicketResponse createTicketResponse = ticketService.createTicket(createTicketRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Ticket created successfully", createTicketResponse));
    }

    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateTicketResponse>> update(@ModelAttribute @Valid UpdateTicketRequest updateTicketRequest){
        UpdateTicketResponse updateTicketResponse = ticketService.updateTicket(updateTicketRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Ticket updated successfully", updateTicketResponse));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<GetTicketResponse>> getTicket(@RequestParam Integer id){
        GetTicketResponse getTicketResponse = ticketService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Id Tour  successfully", getTicketResponse));
    }

    @GetMapping("/ticket")
    public ResponseEntity<ApiResponse<List<GetTicketResponse>>> getTicketByDate(@RequestParam Integer idTourism){
        List<GetTicketResponse> getTicketResponse = ticketService.findAllbyIdTourism(idTourism);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Id Tour  successfully", getTicketResponse));
    }

}
