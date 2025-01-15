package cz.sda.java.remotesk1.invoices.repository;

import cz.sda.java.remotesk1.invoices.model.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, String> {
    List<Client> findByNameContainingIgnoreCase(String name);

    @Query("SELECT c FROM Client c WHERE c.name LIKE :name")
    List<Client> findByName(@Param("name")String name);
}
