package com.demo.demo.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.demo.model.Ticket;
import com.demo.demo.model.TicketStatus;
import com.demo.demo.model.User;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	List<Ticket> findByAssignedEngineerAndStatus(User assignedEngineer, TicketStatus status);
    List<Ticket> findByStatus(TicketStatus status);
}
