//package com.siddardha.InvoiceManagement.Security;
//
//import com.siddardha.InvoiceManagement.Model.Client;
//import com.siddardha.InvoiceManagement.Repository.ClientRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.*;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ClientDetailsService implements UserDetailsService {
//    @Autowired
//    private ClientRepository clientRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Client client = clientRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Client not found"));
//        return User.withUsername(client.getEmail())
//                .password(client.getPassword())
//                .roles("CLIENT")
//                .build();
//    }
//}
