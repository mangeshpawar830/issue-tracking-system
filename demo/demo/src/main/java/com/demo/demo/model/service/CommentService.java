package com.demo.demo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.demo.model.Client;
import com.demo.demo.model.Comment;
import com.demo.demo.model.Ticket;
import com.demo.demo.model.User;
import com.demo.demo.model.repo.ClientRepository;
import com.demo.demo.model.repo.CommentRepository;
import com.demo.demo.model.repo.TicketRepository;
import com.demo.demo.model.repo.UserRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private  UserRepository userRepository;
    
    @Autowired
    ClientRepository clientRepository;


    public void addComment(Comment comment) {
        Long ticketId = comment.getTicket().getId();
        Long userId = comment.getUser().getId();

        if (ticketId != null && ticketId != 0) {
            Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);

            if (optionalTicket.isPresent()) {
                Optional<User> optionalUser = userRepository.findById(userId);

                Comment newComment = new Comment();
                newComment.setText(comment.getText());
                newComment.setTicket(optionalTicket.get());
                newComment.setClient(optionalTicket.get().getClient());
                newComment.setUser(optionalUser.get()); 
                commentRepository.save(newComment);
            } else {
                throw new IllegalArgumentException("Ticket, User, or Client not found.");
            }
        } else {
            throw new IllegalArgumentException("Invalid Ticket, User, or Client ID.");
        }
    }


    public List<Comment> getCommentsByTicket(Ticket ticket) {
        return commentRepository.findByTicket(ticket);
    }
}
