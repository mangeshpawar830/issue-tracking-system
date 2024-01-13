package com.demo.demo.contoller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.demo.model.Comment;
import com.demo.demo.model.Ticket;
import com.demo.demo.model.TicketStatus;
import com.demo.demo.model.User;
import com.demo.demo.model.UserType;
import com.demo.demo.model.service.CommentService;
import com.demo.demo.model.service.TicketService;
import com.demo.demo.model.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private CommentService commentService;

    @GetMapping("/engineer/{id}")
    public ResponseEntity<List<Ticket>> getEngineerTickets(@PathVariable Long id) {
        Optional<User> engineer = userService.getUserById(id);
        if (engineer.isPresent() && engineer.get().getType() == UserType.ENGINEER) {
            List<Ticket> tickets = ticketService.getTicketsForEngineer(engineer.get());
            return ResponseEntity.ok(tickets);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/open")
    public ResponseEntity<List<Ticket>> getAllOpenTickets() {
        List<Ticket> openTickets = ticketService.getAllOpenTickets();
        return ResponseEntity.ok(openTickets);
    }

    @PutMapping("/{ticketId}/assign/{id}")
    public ResponseEntity<Void> assignTicketToEngineer(@PathVariable Long ticketId, @PathVariable Long id) {
        Optional<User> engineer = userService.getUserById(id);
        Optional<Ticket> ticket = ticketService.getTicketById(ticketId);
        if (engineer.isPresent() && ticket.isPresent()) {
            ticketService.assignTicketToEngineer(ticket.get(), engineer.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{ticketId}/status/{status}")
    public ResponseEntity<Void> changeTicketStatus(@PathVariable Long ticketId, @PathVariable String status) {
        Optional<Ticket> ticket = ticketService.getTicketById(ticketId);
        if (ticket.isPresent()) {
            TicketStatus ticketStatus = TicketStatus.valueOf(status.toUpperCase());
            ticketService.changeTicketStatus(ticket.get(), ticketStatus);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketDetails(@PathVariable Long ticketId) {
        Optional<Ticket> ticket = ticketService.getTicketById(ticketId);
        if (ticket.isPresent()) {
            return ResponseEntity.ok(ticket.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/comments/{ticketId}")
    public ResponseEntity<List<Comment>> getTicketComments(@PathVariable Long ticketId) {
        Optional<Ticket> ticket = ticketService.getTicketById(ticketId);
        if (ticket.isPresent()) {
            List<Comment> comments = commentService.getCommentsByTicket(ticket.get());
            return ResponseEntity.ok(comments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
