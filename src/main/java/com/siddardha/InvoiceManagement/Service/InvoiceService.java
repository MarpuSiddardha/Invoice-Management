package com.siddardha.InvoiceManagement.Service;

import com.siddardha.InvoiceManagement.Model.Invoice;
import com.siddardha.InvoiceManagement.Repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
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

}
