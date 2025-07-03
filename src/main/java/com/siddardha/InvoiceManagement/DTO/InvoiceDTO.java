package com.siddardha.InvoiceManagement.DTO;

import com.siddardha.InvoiceManagement.Model.InvoiceItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
    private Long id;
    private LocalDate date;
    private Double total;
    private Long clientId;
    private List<InvoiceItemDTO> items = new ArrayList<>();
    
    // Client details (optional, can be included if needed)
    private String clientName;
    private String clientEmail;
    
    public void addItem(InvoiceItemDTO item) {
        this.items.add(item);
    }
}
