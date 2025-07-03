package com.siddardha.InvoiceManagement.Repository;


import com.siddardha.InvoiceManagement.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
}

