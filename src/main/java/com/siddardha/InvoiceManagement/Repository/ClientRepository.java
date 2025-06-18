package com.siddardha.InvoiceManagement.Repository;


import com.siddardha.InvoiceManagement.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {}

