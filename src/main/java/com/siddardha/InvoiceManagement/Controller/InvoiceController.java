package com.siddardha.InvoiceManagement.Controller;

import com.siddardha.InvoiceManagement.DTO.InvoiceDTO;
import com.siddardha.InvoiceManagement.Mapper.InvoiceMapper;
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
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final PdfGeneratorService pdfGeneratorService;
    private final EmailService emailService;
    private final InvoiceMapper invoiceMapper;

    public InvoiceController(InvoiceService invoiceService, 
                           PdfGeneratorService pdfGeneratorService, 
                           EmailService emailService,
                           InvoiceMapper invoiceMapper) {
        this.invoiceService = invoiceService;
        this.pdfGeneratorService = pdfGeneratorService;
        this.emailService = emailService;
        this.invoiceMapper = invoiceMapper;
    }

    @GetMapping
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceService.getAllInvoices().stream()
                .map(invoiceMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public InvoiceDTO getInvoiceById(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        return invoiceMapper.toDto(invoice);
    }

    @PostMapping
    public InvoiceDTO createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.toEntity(invoiceDTO);
        Invoice savedInvoice = invoiceService.saveInvoice(invoice);
        return invoiceMapper.toDto(savedInvoice);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok("Invoice deleted successfully");
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable Long id, @RequestBody InvoiceDTO invoiceDTO) {
        // Ensure the ID in the path matches the ID in the request body
        if (invoiceDTO.getId() != null && !id.equals(invoiceDTO.getId())) {
            throw new RuntimeException("ID in path does not match ID in request body");
        }
        // Set the ID from path to ensure consistency
        invoiceDTO.setId(id);
        
        Invoice invoice = invoiceMapper.toEntity(invoiceDTO);
        Invoice updatedInvoice = invoiceService.updateInvoice(id, invoice);
        return ResponseEntity.ok(invoiceMapper.toDto(updatedInvoice));
    }
}

