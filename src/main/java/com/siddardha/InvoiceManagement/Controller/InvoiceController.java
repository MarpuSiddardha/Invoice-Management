package com.siddardha.InvoiceManagement.Controller;

import com.siddardha.InvoiceManagement.Model.Invoice;
import com.siddardha.InvoiceManagement.Service.EmailService;
import com.siddardha.InvoiceManagement.Service.InvoiceService;
import com.siddardha.InvoiceManagement.Service.PdfGeneratorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final PdfGeneratorService pdfGeneratorService;
    private final EmailService emailService;

    public InvoiceController(InvoiceService invoiceService, PdfGeneratorService pdfGeneratorService, EmailService emailService) {
        this.invoiceService = invoiceService;
        this.pdfGeneratorService = pdfGeneratorService;
        this.emailService = emailService;
    }

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @PostMapping
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return invoiceService.saveInvoice(invoice);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        ByteArrayInputStream pdf = pdfGeneratorService.generateInvoicePdf(invoice);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=invoice_" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf.readAllBytes());
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<String> sendInvoiceEmail(@PathVariable Long id) throws Exception {
        Invoice invoice = invoiceService.getInvoiceById(id);
        ByteArrayInputStream pdf = pdfGeneratorService.generateInvoicePdf(invoice);

        byte[] pdfBytes = pdf.readAllBytes();
        emailService.sendInvoiceEmail(
                invoice.getClient().getEmail(),
                "Your Invoice #" + invoice.getId(),
                "Please find your invoice attached.",
                () -> new ByteArrayInputStream(pdfBytes),
                "invoice_" + id + ".pdf"
        );

        return ResponseEntity.ok("Invoice sent to " + invoice.getClient().getEmail());
    }



}

