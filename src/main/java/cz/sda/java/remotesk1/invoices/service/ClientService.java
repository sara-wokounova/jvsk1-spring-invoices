package cz.sda.java.remotesk1.invoices.service;

import cz.sda.java.remotesk1.invoices.model.Client;

import java.util.List;

public interface ClientService {
    Client addClient(String name, String address);
    void removeClient(String id);
    Client getClient(String id);
    Client updateClient(String id, Client client);
    List<Client> getAllClients();
}
