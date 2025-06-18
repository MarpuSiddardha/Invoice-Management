package com.siddardha.InvoiceManagement.Model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private Double total;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<InvoiceItem> items;
}

