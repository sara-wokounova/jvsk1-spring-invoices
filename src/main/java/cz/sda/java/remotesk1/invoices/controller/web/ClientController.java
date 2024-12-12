package cz.sda.java.remotesk1.invoices.controller.web;

import cz.sda.java.remotesk1.invoices.model.Client;
import cz.sda.java.remotesk1.invoices.model.UpdateClient;
import cz.sda.java.remotesk1.invoices.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/clients")
class ClientController {

    private final ClientService clientService;

    @Autowired
    ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    String getAllClients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("client", Client.builder().build());
        return "clients";
    }

    @PostMapping("/add")
    String addUser(Client client, Model model) {
        clientService.addClient(client.name(), client.address());
        return "redirect:/clients/";
    }

    @GetMapping("/delete/{id}")
    String addUser(@PathVariable String id, Model model) {
        clientService.removeClient(id);
        return "redirect:/clients/";
    }

    @GetMapping("/edit/{id}")
    String updateUser(@PathVariable String id, Model model) {
        model.addAttribute("client", clientService.getClient(id));
        return "edit-client";
    }

    @PostMapping("/update/{id}")
    String updateUser(@PathVariable String id, UpdateClient client, Model model) {
        clientService.updateClient(id, client);
        return "redirect:/clients/";
    }

}
