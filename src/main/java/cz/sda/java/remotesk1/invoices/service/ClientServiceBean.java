package cz.sda.java.remotesk1.invoices.service;

import cz.sda.java.remotesk1.invoices.model.Client;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
class ClientServiceBean implements ClientService {

    private final Map<String, Client> clients = new HashMap<>();

    @Override
    public void addClient(Client client) {
        if (clients.containsKey(client.id())) {
            throw new IllegalArgumentException("Client with id " + client.id() + " already exists");
        }
        clients.put(client.id(), client);
    }

    @Override
    public void removeClient(String id) {
        if (!clients.containsKey(id)) {
            throw new IllegalArgumentException("Client with id " + id + " does not exist");
        }
        clients.remove(id);
    }

    @Override
    public Client getClient(String id) {
        if (!clients.containsKey(id)) {
            throw new IllegalArgumentException("Client with id " + id + " does not exist");
        }
        return clients.get(id);
    }

    @Override
    public void updateClient(Client client) {
        if (!clients.containsKey(client.id())) {
            throw new IllegalArgumentException("Client with id " + client.id() + " does not exist");
        }
        clients.put(client.id(), client);
    }

    @Override
    public List<Client> getAllClients() {
        return List.of(clients.values().toArray(new Client[0]));
    }
}
