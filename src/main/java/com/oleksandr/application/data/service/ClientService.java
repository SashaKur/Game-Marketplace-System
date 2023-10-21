package com.oleksandr.application.data.service;


import com.oleksandr.application.data.entity.Client;
import com.oleksandr.application.data.repository.ClientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Set<Client> getFriends(Long id){
        Optional<Client> client = clientRepository.getClientById(id);
        assert client.isPresent();

        Set<Client> friends = client.get().getReferencesFrom();

        return friends;
    }

    public Optional<Client> getClientById(Long id){
        return clientRepository.getClientById(id);
    }
}
