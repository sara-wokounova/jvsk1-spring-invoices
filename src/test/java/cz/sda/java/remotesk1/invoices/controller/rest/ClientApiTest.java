package cz.sda.java.remotesk1.invoices.controller.rest;

import cz.sda.java.remotesk1.invoices.model.Client;
import cz.sda.java.remotesk1.invoices.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientApi.class)
class ClientApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService clientService;

    @Test
    void addClientShouldReturnCreatedClient() throws Exception {
        var client = new Client(UUID.randomUUID().toString(), "Jane Doe", "USA");

        Mockito.when(clientService.addClient(Mockito.any(String.class), Mockito.any(String.class))).thenReturn(client);

        mockMvc.perform(post("/rest/clients/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Jane Doe\",\"address\":\"USA\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/clients/" + client.getId()))
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.address").value("USA"));
    }

    @Test
    void getAllClientsShouldReturnClientList() throws Exception {
        var client = new Client(UUID.randomUUID().toString(), "Jane Doe", "USA");

        Mockito.when(clientService.getAllClients()).thenReturn(List.of(client));

        mockMvc.perform(get("/rest/clients/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Jane Doe"))
                .andExpect(jsonPath("$[0].address").value("USA"));
    }

    @Test
    void getClientShouldReturnClient() throws Exception {
        var client = new Client(UUID.randomUUID().toString(), "Jane Doe", "USA");

        Mockito.when(clientService.getClient(Mockito.any(String.class))).thenReturn(client);

        mockMvc.perform(get("/rest/clients/{id}", client.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.address").value("USA"));
    }

    @Test
    void removeClientShouldReturnNoContent() throws Exception {
        var clientId = UUID.randomUUID().toString();

        Mockito.doNothing().when(clientService).removeClient(clientId);

        mockMvc.perform(delete("/rest/clients/{id}", clientId))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateClientShouldReturnUpdatedClient() throws Exception {
        var clientId = UUID.randomUUID().toString();
        var client = new Client(clientId, "Jane Doe", "USA");

        Mockito.when(clientService.updateClient(Mockito.any(String.class), Mockito.any(Client.class))).thenReturn(client);

        mockMvc.perform(patch("/rest/clients/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Jane Doe\",\"address\":\"USA\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.address").value("USA"));
    }
}