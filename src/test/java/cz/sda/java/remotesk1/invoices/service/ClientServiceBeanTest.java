package cz.sda.java.remotesk1.invoices.service;

import cz.sda.java.remotesk1.invoices.model.Client;
import cz.sda.java.remotesk1.invoices.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
class ClientServiceBeanTest {

    @Autowired
    ClientServiceBean clientServiceBean;

    @MockitoBean
    public ClientRepository clientRepository;

    @TestConfiguration
    public static class TestConfig {

        @Bean
        ClientServiceBean clientServiceBean(ClientRepository clientRepository) {
            return new ClientServiceBean(clientRepository);
        }
    }

    @Test
    void addClientShouldPass() {
        // given
        var uuid = UUID.randomUUID().toString();
        Mockito.when(clientRepository.save(Mockito.any(Client.class)))
                .thenAnswer(invocation -> {
                    Client client = invocation.getArgument(0);
                    client.setId(uuid); // Mock assigning an ID to the client
                    return client;
                });
        // when
        var client = clientServiceBean.addClient("Jane Doe", "USA");
        // then
        Mockito.verify(clientRepository, Mockito.times(1)).save(Mockito.any(Client.class));
        assert client.getName().equals("Jane Doe");
        assert client.getAddress().equals("USA");
        assert client.getId().equals(uuid);
    }
}
