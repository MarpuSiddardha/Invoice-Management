package com.siddardha.InvoiceManagement.Mapper;

import com.siddardha.InvoiceManagement.DTO.InvoiceDTO;
import com.siddardha.InvoiceManagement.DTO.InvoiceItemDTO;
import com.siddardha.InvoiceManagement.Model.Client;
import com.siddardha.InvoiceManagement.Model.Invoice;
import com.siddardha.InvoiceManagement.Model.InvoiceItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {
    
    public InvoiceDTO toDto(Invoice invoice) {
        if (invoice == null) {
            return null;
        }
        
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(invoice.getId());
        dto.setDate(invoice.getDate());
        dto.setTotal(invoice.getTotal());
        
        if (invoice.getClient() != null) {
            dto.setClientId(invoice.getClient().getId());
            dto.setClientName(invoice.getClient().getName());
            dto.setClientEmail(invoice.getClient().getEmail());
        }
        
        if (invoice.getItems() != null) {
            List<InvoiceItemDTO> itemDTOs = invoice.getItems().stream()
                    .map(this::toItemDto)
                    .collect(Collectors.toList());
            dto.setItems(itemDTOs);
        }
        
        return dto;
    }

    public Invoice toEntity(InvoiceDTO dto) {
        if (dto == null) {
            return null;
        }

        Invoice invoice = new Invoice();
        invoice.setId(dto.getId());
        invoice.setDate(dto.getDate());
        invoice.setTotal(dto.getTotal());

        // Set client from clientId
        if (dto.getClientId() != null) {
            Client client = new Client();
            client.setId(dto.getClientId());
            invoice.setClient(client);
        }

        // Set items
        if (dto.getItems() != null) {
            List<InvoiceItem> items = dto.getItems().stream()
                    .map(this::toItemEntity)
                    .collect(Collectors.toList());
            // Set invoice reference for each item
            items.forEach(item -> item.setInvoice(invoice));
            invoice.setItems(items);
        }

        return invoice;
    }
    
    public InvoiceItemDTO toItemDto(InvoiceItem item) {
        if (item == null) {
            return null;
        }
        
        InvoiceItemDTO dto = new InvoiceItemDTO();
        dto.setId(item.getId());
        dto.setDescription(item.getDescription());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        
        return dto;
    }
    
    public InvoiceItem toItemEntity(InvoiceItemDTO dto) {
        if (dto == null) {
            return null;
        }
        
        InvoiceItem item = new InvoiceItem();
        item.setId(dto.getId());
        item.setDescription(dto.getDescription());
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());
        
        return item;
    }
}
