package cz.sda.java.remotesk1.invoices.controller.rest.request;

import java.time.LocalDate;

public record UpdateOrder(String clientId, LocalDate date) {}
