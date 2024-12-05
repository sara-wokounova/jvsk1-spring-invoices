package cz.sda.java.remotesk1.invoices.controller;

import cz.sda.java.remotesk1.invoices.controller.request.CreateClient;
import cz.sda.java.remotesk1.invoices.controller.request.UpdateClient;
import cz.sda.java.remotesk1.invoices.model.Client;
import cz.sda.java.remotesk1.invoices.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clients")
class ClientController {

    private final ClientService clientService;

    @Autowired
    ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/")
    ResponseEntity<Client> addClient(@RequestBody CreateClient client) {
        Client created = clientService.addClient(client.name(), client.address());
        return ResponseEntity.created(URI.create("/clients/" + created.id())).body(created);
    }

    @GetMapping("/")
    List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    Client getClient(@NonNull @PathVariable("id") String id) {
        return clientService.getClient(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> removeClient(@PathVariable("id") String id) {
        clientService.removeClient(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    ResponseEntity<Client> updateClient(@PathVariable("id") String id, @RequestBody UpdateClient client) {
        var updated = clientService.updateClient(id, client);
        return ResponseEntity.ok(updated);
    }

}
