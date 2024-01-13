package com.demo.demo.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.demo.model.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findBySupportEmail(String supportEmail);

}
