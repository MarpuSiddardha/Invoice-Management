package com.siddardha.InvoiceManagement.Repository;


import com.siddardha.InvoiceManagement.Model.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {}

