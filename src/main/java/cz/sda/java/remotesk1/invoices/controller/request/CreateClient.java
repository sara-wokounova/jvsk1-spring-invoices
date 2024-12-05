package cz.sda.java.remotesk1.invoices.controller.request;

import lombok.NonNull;

public record CreateClient(@NonNull String name, @NonNull String address) {
}
