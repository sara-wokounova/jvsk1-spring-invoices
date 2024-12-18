package cz.sda.java.remotesk1.invoices.controller.web;

import cz.sda.java.remotesk1.invoices.controller.web.request.UpdateClient;
import cz.sda.java.remotesk1.invoices.model.Client;
import cz.sda.java.remotesk1.invoices.service.ClientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        setDefaultValues(model);
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("client", new Client());
        return "clients";
    }

    @PostMapping("/add")
    String addUser(Client client, Model model) {
        setDefaultValues(model);
        clientService.addClient(client.getName(), client.getAddress());
        return "redirect:/clients/";
    }

    @GetMapping("/delete/{id}")
    String addUser(@PathVariable String id, Model model) {
        setDefaultValues(model);
        clientService.removeClient(id);
        return "redirect:/clients/";
    }

    @GetMapping("/edit/{id}")
    String updateUser(@PathVariable String id, Model model) {
        setDefaultValues(model);
        model.addAttribute("updateClient", clientService.getClient(id));
        return "edit-client";
    }

    @PostMapping("/update/{id}")
    String updateUser(@PathVariable String id, @Valid UpdateClient client, BindingResult result, Model model) {
        setDefaultValues(model);
        if (result.hasErrors()) {
            client.setId(id);
            model.addAttribute("updateClient", client);
            return "edit-client";
        }

        clientService.updateClient(client.getId(), new Client(client.getId(), client.getName(), client.getAddress()));
        return "redirect:/clients/";
    }

    private void setDefaultValues(Model model) {
        model.addAttribute("pageTitle", "Client");
    }

}
