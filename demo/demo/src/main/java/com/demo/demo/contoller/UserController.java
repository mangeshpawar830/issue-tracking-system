package com.demo.demo.contoller;



import java.security.Principal;
import java.util.Collections;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.demo.model.LoginRequest;
import com.demo.demo.model.Ticket;
import com.demo.demo.model.User;
import com.demo.demo.model.UserType;
import com.demo.demo.model.service.TicketService;
import com.demo.demo.model.service.UserService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        String loginId = loginRequest.getLoginId();
        String password = loginRequest.getPassword();

        User authenticatedUser = userService.authenticate(loginId, password);

        if (authenticatedUser != null) {
            session.setAttribute("user", authenticatedUser);
            return ResponseEntity.ok().body(Collections.singletonMap("user", authenticatedUser));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid credentials"));
        }
    }
    
    
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            if (UserType.ENGINEER.equals(user.getType())) {
                List<Ticket> assignedTickets = ticketService.getAssignedTickets(user);
                return ResponseEntity.ok().body(Collections.singletonMap("tickets", assignedTickets));
            } else if (UserType.MANAGER.equals(user.getType())) {
                List<Ticket> openTickets = ticketService.getAllOpenTickets();
                return ResponseEntity.ok().body(Collections.singletonMap("tickets", openTickets));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid credentials"));
    }
    
    
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
 


}

