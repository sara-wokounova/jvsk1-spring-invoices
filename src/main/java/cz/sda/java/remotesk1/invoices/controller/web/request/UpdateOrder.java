package cz.sda.java.remotesk1.invoices.controller.web.request;

import lombok.NonNull;

import java.time.LocalDate;

public record UpdateOrder(@NonNull String orderId, String clientId, LocalDate date) {}
