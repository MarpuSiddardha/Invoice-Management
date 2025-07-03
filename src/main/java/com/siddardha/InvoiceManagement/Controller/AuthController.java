//package com.siddardha.InvoiceManagement.Controller;
//
//
//import com.siddardha.InvoiceManagement.Model.Client;
//import com.siddardha.InvoiceManagement.Repository.ClientRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins = "*")
//@RestController
//public class AuthController {
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/register")
//    public String register(@RequestBody Client client) {
//        client.setPassword(passwordEncoder.encode(client.getPassword()));
//        clientRepository.save(client);
//        return "Registration successful";
//    }
//
//    // For /login, Spring Security handles authentication automatically.
//}