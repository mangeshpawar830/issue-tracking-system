package com.demo.demo.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.demo.model.Comment;
import com.demo.demo.model.Ticket;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
    List<Comment> findByTicket(Ticket ticket);

}
