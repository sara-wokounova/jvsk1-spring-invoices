package cz.sda.java.remotesk1.invoices.service;

import cz.sda.java.remotesk1.invoices.controller.request.UpdateClient;
import cz.sda.java.remotesk1.invoices.exception.NotFoundException;
import cz.sda.java.remotesk1.invoices.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
class ClientServiceBean implements ClientService {

    private final Map<String, Client> clients = new HashMap<>();

    @Override
    public Client addClient(String name, String address) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        if (!StringUtils.hasText(address)) {
            throw new IllegalArgumentException("Address must not be empty");
        }
        var client = new Client(UUID.randomUUID().toString(), name, address);
        if (clients.containsKey(client.id())) {
            throw new IllegalArgumentException("Client with id " + client.id() + " already exists");
        }
        clients.put(client.id(), client);
        log.info("Client added: {}", client);
        return client;
    }

    @Override
    public void removeClient(String id) {
        if (!clients.containsKey(id)) {
            throw new NotFoundException("Client with id " + id + " does not exist");
        }
        clients.remove(id);
        log.info("Client removed: {}", id);
    }

    @Override
    public Client getClient(String id) {
        if (!clients.containsKey(id)) {
            throw new NotFoundException("Client with id " + id + " does not exist");
        }
        return clients.get(id);
    }

    @Override
    public Client updateClient(String id, UpdateClient updateClient) {
        if (!clients.containsKey(id)) {
            throw new NotFoundException("Client with id " + id + " does not exist");
        }
        var client = clients.get(id);
        var builder = Client.builder().id(id);
        if (StringUtils.hasText(updateClient.name())) {
            builder.name(updateClient.name());
        } else {
            builder.name(client.name());
        }
        if (StringUtils.hasText(updateClient.address())) {
            builder.address(updateClient.address());
        } else {
            builder.address(client.address());
        }
        var updated = builder.build();
        clients.put(id, updated);
        log.info("Client updated: {}", updated);
        return updated;
    }

    @Override
    public List<Client> getAllClients() {
        return List.of(clients.values().toArray(new Client[0]));
    }
}
