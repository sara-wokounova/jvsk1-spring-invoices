package cz.sda.java.remotesk1.invoices.controller.rest.request;

import lombok.NonNull;

import java.time.LocalDate;

public record CreateOrder (
    @NonNull
    String clientId,
    @NonNull
    LocalDate date
    ) {}
