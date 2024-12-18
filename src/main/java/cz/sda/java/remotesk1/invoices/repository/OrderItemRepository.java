package cz.sda.java.remotesk1.invoices.repository;

import cz.sda.java.remotesk1.invoices.model.OrderItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem, String> {
    List<OrderItem> findAllByOrderId(String orderId);
}
