package cz.sda.java.remotesk1.invoices.controller;

import cz.sda.java.remotesk1.invoices.model.Client;
import cz.sda.java.remotesk1.invoices.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

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
    void addClient(Client client) {
        clientService.addClient(client);
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
    void removeClient(@PathVariable("id") String id) {
        clientService.removeClient(id);
    }

    @PutMapping("/{id}")
    void updateClient(@PathVariable("id") String id, Client client) {
        clientService.updateClient(client);
    }

}
