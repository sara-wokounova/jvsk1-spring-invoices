package cz.sda.java.remotesk1.invoices.repository;

import cz.sda.java.remotesk1.invoices.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, String> {
    List<Product> findByNameContainingIgnoreCase(String name);
}
