package co.empresa.productoservice.domain.service;


import co.empresa.productoservice.domain.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interface que define los métodos que se pueden realizar sobre la entidad Cliente
 */
public interface IClientService {
    Client save(Client client);
    void delete(Client client);
    Optional<Client> findById(Long id);
    Client update(Client client);
    List<Client> findAll();
    Page<Client> findAll(Pageable pageable);
}
