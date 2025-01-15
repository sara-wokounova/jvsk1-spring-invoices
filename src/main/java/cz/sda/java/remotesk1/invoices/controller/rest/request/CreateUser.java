package cz.sda.java.remotesk1.invoices.controller.rest.request;

import lombok.NonNull;

public record CreateUser(@NonNull String username, @NonNull String password) {
}
