package cz.sda.java.remotesk1.invoices.controller.rest.request;

import lombok.NonNull;

public record CreateOrderItem(@NonNull String productId, int amount) {
}
