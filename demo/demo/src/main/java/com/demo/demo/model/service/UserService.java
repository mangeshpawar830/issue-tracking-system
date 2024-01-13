package com.demo.demo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.demo.model.User;
import com.demo.demo.model.UserType;
import com.demo.demo.model.repo.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	

	    public Optional<User> getUserById(Long id) {
	        return userRepository.findById(id);
	    }
	    
	  

	        public User authenticate(String loginId, String password) {
	            User user = userRepository.findByLoginId(loginId);

	            if (user != null && user.getPassword().equals(password)) {
	                return user; 
	            } else {
	                return null;
	            }
	        }
	        
	        
	        public List<User> getAllUsers() {
	            return userRepository.findAll();
	        }
	    

	    
	   
	 
	

}
