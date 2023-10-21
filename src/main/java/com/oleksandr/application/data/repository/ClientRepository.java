package com.oleksandr.application.data.repository;

import com.oleksandr.application.data.entity.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Set<Client> getReferenceToById(Long id);

    Optional<Client> getClientById(Long id);
}
