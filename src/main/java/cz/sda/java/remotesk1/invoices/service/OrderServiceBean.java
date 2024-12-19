package cz.sda.java.remotesk1.invoices.service;

import cz.sda.java.remotesk1.invoices.exception.NotFoundException;
import cz.sda.java.remotesk1.invoices.model.Order;
import cz.sda.java.remotesk1.invoices.model.OrderItem;
import cz.sda.java.remotesk1.invoices.repository.ClientRepository;
import cz.sda.java.remotesk1.invoices.repository.OrderItemRepository;
import cz.sda.java.remotesk1.invoices.repository.OrderRepository;
import cz.sda.java.remotesk1.invoices.repository.ProductRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
class OrderServiceBean implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    OrderServiceBean(OrderRepository orderRepository, ClientRepository clientRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Order addOrder(@NonNull String clientId, @NonNull LocalDate date) {
        var client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        var order = new Order(UUID.randomUUID().toString(), client, date);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), true)
                .toList();
    }

    @Override
    public Order getOrder(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found " + id));
    }

    @Override
    public void removeOrder(String id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found " + id));
        orderRepository.delete(order);
    }

    @Override
    public Order updateOrder(String id, String clientId, LocalDate date) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found " + id));
        if (StringUtils.hasText(clientId)) {
            var client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new IllegalArgumentException("Client not found" + clientId));
            order.setClient(client);
        }
        if (date != null) {
            order.setDate(date);
        }
        orderRepository.save(order);
        return order;
    }

    @Override
    public OrderItem addItemToOrder(String orderId, @NonNull String productId, int amount) {
        var order = getOrder(orderId);
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        var orderItem = new OrderItem(UUID.randomUUID().toString(), order, product,amount);
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getAllItemsFor(String orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }

    @Override
    public OrderItem getOrderItem(String orderId, String id) {
        var orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order item not found " + id));
        if (!orderItem.getOrder().getId().equals(orderId)) {
            throw new IllegalArgumentException("Order item not found in order " + orderId);
        }
        return orderItem;
    }

    @Override
    public void removeOrderItem(String orderId, String id) {
        var orderItem = getOrderItem(orderId, id);
        orderItemRepository.delete(orderItem);
    }

    @Override
    public OrderItem updateOrderItem(String orderId, String id, String productId, Integer amount) {
        var orderItem = getOrderItem(orderId, id);
        if (StringUtils.hasText(productId)) {
            var product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
            orderItem.setProduct(product);
        }
        if (amount != null) {
            orderItem.setAmount(amount);
        }
        return orderItemRepository.save(orderItem);
    }
}
