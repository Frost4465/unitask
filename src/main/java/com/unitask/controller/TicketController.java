package com.unitask.controller;

import com.unitask.dto.ticket.TicketBoardResponse;
import com.unitask.dto.ticket.TicketRequest;
import com.unitask.service.TickerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path = "ticket")
public class TicketController {

    @Autowired
    private TickerService ticketService;

    @PostMapping("/createTicket")
    public ResponseEntity<?> createTicket(@Valid @RequestBody TicketRequest ticketRequest) {
        ticketService.createTicket(ticketRequest);
        return ResponseEntity.ok("Ticket created");
    }

    @PutMapping("/updateTicket/{id}")
    public ResponseEntity<?> updateTicket(@PathVariable("id") Long id, @Valid @RequestBody TicketRequest updateTicketRequest) {
        ticketService.updateTicket(id, updateTicketRequest);
        return ResponseEntity.ok("Ticket updated");
    }

    @PutMapping("/updateTicket/{id}/status")
    public ResponseEntity<?> updateTicketStatus(@PathVariable("id") Long id, @RequestBody TicketRequest updateTicketRequest) {
        ticketService.updateTicketStatus(id, updateTicketRequest);
        return ResponseEntity.ok("Ticket updated");
    }

    @GetMapping("/getTicket/{id}")
    public ResponseEntity<?> getTicket(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(ticketService.getTicket(id));
    }

    @DeleteMapping("/deleteTicket/{ticketId}")
    public ResponseEntity<?> deleteTicket(@Valid @PathVariable("ticketId") Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.ok("Ticket deleted");
    }

    @GetMapping("/ticketList/{id}")
    public ResponseEntity<List<TicketBoardResponse>> ticketList(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(ticketService.projectTicketList(id));
    }
}
