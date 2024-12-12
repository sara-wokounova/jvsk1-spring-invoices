package cz.sda.java.remotesk1.invoices.controller.rest;

import cz.sda.java.remotesk1.invoices.controller.rest.request.CreateClient;
import cz.sda.java.remotesk1.invoices.controller.rest.request.UpdateClient;
import cz.sda.java.remotesk1.invoices.exception.NotFoundException;
import cz.sda.java.remotesk1.invoices.model.Client;
import cz.sda.java.remotesk1.invoices.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rest/clients")
class ClientApi {

    private final ClientService clientService;

    @Autowired
    ClientApi(ClientService clientService) {
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

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.debug("Client not found", e);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.debug("Client not found", e);
        return ResponseEntity.notFound().build();
    }
}
