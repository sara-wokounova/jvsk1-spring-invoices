package cz.sda.java.remotesk1.invoices.repository;

import cz.sda.java.remotesk1.invoices.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, String> {
    List<Client> findByNameContainingIgnoreCase(String name);
}
