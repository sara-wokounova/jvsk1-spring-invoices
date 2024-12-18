package cz.sda.java.remotesk1.invoices.controller.rest;

import cz.sda.java.remotesk1.invoices.controller.rest.request.CreateOrder;
import cz.sda.java.remotesk1.invoices.controller.rest.request.UpdateOrder;
import cz.sda.java.remotesk1.invoices.exception.NotFoundException;
import cz.sda.java.remotesk1.invoices.model.Order;
import cz.sda.java.remotesk1.invoices.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rest/orders")
class OrderApi {

    private final OrderService orderService;

    @Autowired
    OrderApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    ResponseEntity<Order> addOrder(@RequestBody CreateOrder order) {
        Order created = orderService.addOrder(order.clientId(), order.date());
        return ResponseEntity.created(URI.create("/orders/" + created.getId())).body(created);
    }

    @GetMapping("/")
    List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    Order getOrder(@NonNull @PathVariable("id") String id) {
        return orderService.getOrder(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> removeOrder(@PathVariable("id") String id) {
        orderService.removeOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    ResponseEntity<Order> updateOrder(@PathVariable("id") String id, @RequestBody UpdateOrder order) {
        var updated = orderService.updateOrder(id, order.clientId(), order.date());
        return ResponseEntity.ok(updated);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.debug("Order not found", e);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.debug("Order not found", e);
        return ResponseEntity.notFound().build();
    }
}
