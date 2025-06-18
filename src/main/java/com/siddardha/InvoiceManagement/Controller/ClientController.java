package com.siddardha.InvoiceManagement.Controller;

import com.siddardha.InvoiceManagement.Model.Client;
import com.siddardha.InvoiceManagement.Repository.ClientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // Create a new client
    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    // Get all clients
    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Get a client by ID
    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }
}

