package cz.sda.java.remotesk1.invoices.service;

import cz.sda.java.remotesk1.invoices.exception.NotFoundException;
import cz.sda.java.remotesk1.invoices.model.Client;
import cz.sda.java.remotesk1.invoices.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Slf4j
@Service
class ClientServiceBean implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    ClientServiceBean(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client addClient(String name, String address) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        if (!StringUtils.hasText(address)) {
            throw new IllegalArgumentException("Address must not be empty");
        }
        var client = new Client(UUID.randomUUID().toString(), name, address);
        if (clientRepository.existsById(client.getId())) {
            throw new IllegalArgumentException("Client with id " + client.getId() + " already exists");
        }
        clientRepository.save(client);
        log.info("Client added: {}", client);
        return client;
    }

    @Override
    public void removeClient(String id) {
        if (!clientRepository.existsById(id)) {
            throw new NotFoundException("Client with id " + id + " does not exist");
        }
        clientRepository.deleteById(id);
        log.info("Client removed: {}", id);
    }

    @Override
    public Client getClient(String id) {
        return clientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id " + id + " does not exist"));
    }

    @Override
    public Client updateClient(String id, Client updateClient) {

        var client = clientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id " + id + " does not exist"));
        var updated = new Client();
        if (StringUtils.hasText(updateClient.getName())) {
            updated.setName(updateClient.getName());
        } else {
            updated.setName(client.getName());
        }
        if (StringUtils.hasText(updateClient.getAddress())) {
            updated.setAddress(updateClient.getAddress());
        } else {
            updated.setAddress(client.getAddress());
        }
        clientRepository.save(updated);
        log.info("Client updated: {}", updated);
        return updated;
    }

    @Override
    public List<Client> getAllClients() {
        return StreamSupport.stream(clientRepository.findAll().spliterator(), false).toList();
    }
}
