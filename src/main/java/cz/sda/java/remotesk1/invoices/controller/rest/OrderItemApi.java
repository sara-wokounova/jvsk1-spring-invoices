package cz.sda.java.remotesk1.invoices.controller.rest;

import cz.sda.java.remotesk1.invoices.controller.rest.request.CreateOrderItem;
import cz.sda.java.remotesk1.invoices.controller.rest.request.UpdateOrderItem;
import cz.sda.java.remotesk1.invoices.exception.NotFoundException;
import cz.sda.java.remotesk1.invoices.model.OrderItem;
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
@RequestMapping("/rest/orders/{orderid}/items")
class OrderItemApi {

    private final OrderService orderService;

    @Autowired
    OrderItemApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    ResponseEntity<OrderItem> addOrderItem(@NonNull @PathVariable("orderid") String orderId, @RequestBody CreateOrderItem orderItem) {
        OrderItem created = orderService.addItemToOrder(orderId, orderItem.productId(), orderItem.amount());
        return ResponseEntity.created(
                URI.create(String.format("/orders/%s/items/%s", created.getOrder().getId(), created.getId()))
        ).body(created);
    }

    @GetMapping("/")
    List<OrderItem> getAllOrderItems(@NonNull @PathVariable("orderid") String orderId) {
        return orderService.getAllItemsFor(orderId);
    }

    @GetMapping("/{id}")
    OrderItem getOrder(@NonNull @PathVariable("orderid") String orderId, @NonNull @PathVariable("id") String id) {
        return orderService.getOrderItem(orderId, id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> removeOrderItem(@NonNull @PathVariable("orderid") String orderId, @PathVariable("id") String id) {
        orderService.removeOrderItem(orderId, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    ResponseEntity<OrderItem> updateOrderItem(@NonNull @PathVariable("orderid") String orderId, @PathVariable("id") String id, @RequestBody UpdateOrderItem order) {
        var updated = orderService.updateOrderItem(orderId, id, order.productId(), order.amount());
        return ResponseEntity.ok(updated);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.debug("Order item bad request", e);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.debug("Order item not found", e);
        return ResponseEntity.notFound().build();
    }
}
