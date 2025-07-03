package com.siddardha.InvoiceManagement.Service;

import com.siddardha.InvoiceManagement.Model.Invoice;
import com.siddardha.InvoiceManagement.Model.InvoiceItem;
import com.siddardha.InvoiceManagement.Repository.InvoiceItemRepository;
import com.siddardha.InvoiceManagement.Repository.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceItemRepository invoiceItemRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice saveInvoice(Invoice invoice) {
        if (invoice.getItems() != null) {
            invoice.getItems().forEach(item -> item.setInvoice(invoice));
        }
        return invoiceRepository.save(invoice);
    }


    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findByIdWithItems(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
    }

    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findByIdWithItems(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
        invoiceRepository.delete(invoice);
    }

    @Transactional
    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        // Get the existing invoice
        Invoice invoice = getInvoiceById(id);
        
        // Update basic fields
        invoice.setDate(invoiceDetails.getDate());
        invoice.setTotal(invoiceDetails.getTotal());
        
        // Update client reference if provided
        if (invoiceDetails.getClient() != null) {
            // We only need to set the client ID, not the entire client object
            // The client will be loaded when needed (lazily)
            invoice.setClient(invoiceDetails.getClient());
        }
        
        // Handle items update
        if (invoiceDetails.getItems() != null) {
            // Clear existing items
            invoice.getItems().clear();
            
            // Add all new items
            if (!invoiceDetails.getItems().isEmpty()) {
                for (InvoiceItem item : invoiceDetails.getItems()) {
                    item.setId(null); // Ensure new items get new IDs
                    item.setInvoice(invoice);
                    invoice.getItems().add(item);
                }
            }
        }
        
        // Save and return the updated invoice
        return invoiceRepository.save(invoice);
    }
}
