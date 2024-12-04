package cz.sda.java.remotesk1.invoices.service;

import cz.sda.java.remotesk1.invoices.model.Client;

import java.util.List;

public interface ClientService {
    void addClient(Client client);
    void removeClient(String id);
    Client getClient(String id);
    void updateClient(Client client);
    List<Client> getAllClients();
}
