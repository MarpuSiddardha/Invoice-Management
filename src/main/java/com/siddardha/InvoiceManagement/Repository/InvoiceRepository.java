package com.siddardha.InvoiceManagement.Repository;


import com.siddardha.InvoiceManagement.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT i FROM Invoice i LEFT JOIN FETCH i.items WHERE i.id = :id")
    Optional<Invoice> findByIdWithItems(@Param("id") Long id);

}

