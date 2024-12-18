package cz.sda.java.remotesk1.invoices.repository;

import cz.sda.java.remotesk1.invoices.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, String> {
    List<Order> findByClientNameIgnoreCase(String clientName);
    List<Order> findByClientId(String clientId);
}
