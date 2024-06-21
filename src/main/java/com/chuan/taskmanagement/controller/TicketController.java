package com.chuan.taskmanagement.controller;

import com.chuan.taskmanagement.dto.CreateTicketRequest;
import com.chuan.taskmanagement.dto.UpdateTicketRequest;
import com.chuan.taskmanagement.service.TickerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(path = "ticket")
public class TicketController {

    @Autowired
    private TickerService ticketService;

    @PostMapping("/createTicket")
    public ResponseEntity<?> createTicket(@Valid @RequestBody CreateTicketRequest ticketRequest) {
        ticketService.createTicket(ticketRequest);
        return ResponseEntity.ok("Ticket created");
    }

    @PutMapping("/updateTicket")
    public ResponseEntity<?> updateTicket(@Valid @RequestBody UpdateTicketRequest updateTicketRequest) {
        ticketService.updateTicket(updateTicketRequest);
        return ResponseEntity.ok("Ticket updated");
    }

    @GetMapping("/getTicket/{ticketId}")
    public ResponseEntity<?> getTicket(@Valid @PathVariable("ticketId") Long ticketId) {
        return ResponseEntity.ok(ticketService.getTicket(ticketId)
        );
    }

    @DeleteMapping("/deleteTicket/{ticketId}")
    public ResponseEntity<?> deleteTicket(@Valid @PathVariable("ticketId") Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.ok("Ticket deleted");
    }

    @GetMapping("/ticketList")
    public ResponseEntity<?> ticketList() {
        return ResponseEntity.ok(ticketService.ticketList());
    }

}
