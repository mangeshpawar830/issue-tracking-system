package com.demo.demo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.demo.model.Ticket;
import com.demo.demo.model.TicketStatus;
import com.demo.demo.model.User;
import com.demo.demo.model.repo.TicketRepository;
import com.demo.demo.model.repo.UserRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Ticket> getTicketsForEngineer(User assignedEngineer) {
        return ticketRepository.findByAssignedEngineerAndStatus(assignedEngineer, TicketStatus.OPEN);
    }

    public List<Ticket> getAllOpenTickets() {
        return ticketRepository.findByStatus(TicketStatus.OPEN);
    }

    public void assignTicketToEngineer(Ticket ticket, User assignedEngineer) {
        ticket.setAssignedEngineer(assignedEngineer);
        ticketRepository.save(ticket);
    }

    public void changeTicketStatus(Ticket ticket, TicketStatus status) {
        ticket.setStatus(status);
        ticketRepository.save(ticket);
    }

    public Optional<Ticket> getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId);
    }
    
    public List<Ticket> getAssignedTickets(User engineer) {
        return ticketRepository.findByAssignedEngineerAndStatus(engineer, TicketStatus.OPEN);
    }

}
