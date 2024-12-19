package cz.sda.java.remotesk1.invoices.controller.web;

import cz.sda.java.remotesk1.invoices.controller.web.request.CreateOrder;
import cz.sda.java.remotesk1.invoices.controller.web.request.CreateOrderItem;
import cz.sda.java.remotesk1.invoices.controller.web.request.UpdateOrder;
import cz.sda.java.remotesk1.invoices.service.ClientService;
import cz.sda.java.remotesk1.invoices.service.OrderService;
import cz.sda.java.remotesk1.invoices.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
class OrderController {

    private final OrderService orderService;
    private final ClientService clientService;
    private final ProductService productService;

    @Autowired
    OrderController(OrderService orderService, ClientService clientService, ProductService productService) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String getAllOrders(Model model) {
        setDefaultValues(model);
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("createOrder", new CreateOrder());
        model.addAttribute("clientList", clientService.getAllClients());
        return "order-list";
    }

    @GetMapping("/edit/{id}")
    public String getOrderById(@PathVariable("id") String id, Model model) {
        setDefaultValues(model);
        var order = orderService.getOrder(id);
        model.addAttribute("updateOrder", new UpdateOrder(order.getId(), order.getClient().getId(), order.getDate()));
        model.addAttribute("clientList", clientService.getAllClients());
        model.addAttribute("orderItems", orderService.getAllItemsFor(id));
        model.addAttribute("createOrderItem", new CreateOrderItem());
        model.addAttribute("productList", productService.getAllProducts());
        return "order-edit";
    }

    @PostMapping("/add")
    public String addOrder(CreateOrder order) {
        orderService.addOrder(order.getClientId(), order.getDate());
        return "redirect:/orders/";
    }

    @PostMapping("/update")
    public String updateOrder(UpdateOrder order, Model model) {
        orderService.updateOrder(order.orderId(), order.clientId(), order.date());
        return "redirect:/orders/";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") String id, Model model) {
        orderService.removeOrder(id);
        return "redirect:/orders/";
    }

    @PostMapping("/{id}/items/add")
    public String addOrderItem(@PathVariable("id") String orderId, CreateOrderItem orderItem, Model model) {
        setDefaultValues(model);
        orderService.addItemToOrder(orderId, orderItem.getProductId(), orderItem.getAmount());
        return "redirect:/orders/edit/" + orderId;
    }

    @GetMapping("/{orderId}/items/{itemId}/delete")
    public String deleteOrderItem(@PathVariable("orderId") String orderId, @PathVariable("itemId") String itemId, Model model) {
        setDefaultValues(model);
        orderService.removeOrderItem(orderId, itemId);
        return "redirect:/orders/edit/" + orderId;
    }

    private void setDefaultValues(Model model) {
        model.addAttribute("pageTitle", "Order");
    }
}
