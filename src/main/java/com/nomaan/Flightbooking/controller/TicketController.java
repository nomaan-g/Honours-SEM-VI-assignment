package com.nomaan.Flightbooking.controller;

import com.nomaan.Flightbooking.model.Ticket;
import com.nomaan.Flightbooking.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // Create a new ticket
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = ticketService.createTicket(ticket);
        return ResponseEntity.status(201).body(createdTicket);
    }

    // Get all tickets
    @GetMapping
    public String getAllTickets(Model model) {
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "tickets";  // Thymeleaf template for listing all tickets
    }

    // Get ticket by ID and display in table format
    @GetMapping("/{id}")
    public String getTicketById(@PathVariable String id, Model model) {
        Optional<Ticket> ticket = ticketService.getTicketById(id);
        if (ticket.isPresent()) {
            model.addAttribute("ticket", ticket.get());  // Add the ticket to the model
            return "ticket";  // Return Thymeleaf template for displaying a single ticket
        } else {
            return "redirect:/tickets";  // Redirect if ticket is not found
        }
    }

    // Cancel ticket by ID (Update status to "CANCELLED")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelTicket(@PathVariable String id) {
        boolean isCancelled = ticketService.cancelTicket(id);
        if (isCancelled) {
            return ResponseEntity.ok("Ticket with ID " + id + " has been cancelled successfully.");
        } else {
            return ResponseEntity.status(404).body("Ticket with ID " + id + " not found.");
        }
    }
}
