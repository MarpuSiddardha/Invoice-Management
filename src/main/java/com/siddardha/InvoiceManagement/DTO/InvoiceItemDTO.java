package com.siddardha.InvoiceManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItemDTO {
    private Long id;
    private String description;
    private Integer quantity;
    private Double price;
}
